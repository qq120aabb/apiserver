/**
 * @title: EntityUtil.java
 * @package com.wehealth.weiyu.common.data.mybatis
 * @description: TODO
 * @author hushsh
 * @date 2016年12月30日下午5:28:11
 * @version V1.0
 */

package com.apiserver.data.mybatis.util;


import com.apiserver.data.annotation.NameStyle;
import com.apiserver.data.annotation.Style;
import com.apiserver.data.annotation.Table;
import com.apiserver.data.mybatis.model.EntityTable;
import com.apiserver.data.mybatis.reflet.EntityField;
import com.apiserver.data.mybatis.reflet.FieldUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;


import javax.persistence.Column;
import javax.persistence.Id;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description TODO
 * @author hs
 * @date 2016年12月30日下午5:28:11
 */
@Slf4j
public class EntityUtil {

	/**
	 * 存储实体类对象
	 */
	private static final Map<Class<?>, EntityTable> entityTableMap = new ConcurrentHashMap<>();

	/**
	 * 获取表对象
	 *
	 * @return
	 */
	public static EntityTable getEntityTable(Object obj) {
		EntityTable entityTable = getEntityTableWithoutParams(obj);
		setParams(entityTable, obj);
		return entityTable;
	}

	private static void setParams(EntityTable entityTable, Object obj) {
		// Map<String, Object> allParams = null;
		// 设置params
		if (entityTable.getProperties() != null
				&& entityTable.getProperties().size() > 0) {

			entityTable.setParams(
					getGetterParams(entityTable.getProperties(), obj));
			// allParams = new HashMap<>(entityTable.getParams());
		}

		if (entityTable.getTransientProperties() != null
				&& entityTable.getTransientProperties().size() > 0) {
			entityTable.setTransientParams(getTransientGetterParams(
					entityTable.getTransientProperties(), obj));
			// if (allParams == null) {
			// allParams = new HashMap<>(entityTable.getTransientParams());
			// } else
			// allParams.putAll(entityTable.getTransientParams());
		}
		// if (allParams != null) {
		// entityTable.setAllParams(allParams);
		// }

	}

	private synchronized static Map<String, Object> getGetterParams(
			Map<String, String> properties, Object obj) {
		Map<String, Object> params = new HashMap<>();
		Class<?> clazz = obj.getClass();
		for (Map.Entry<String, String> en : properties.entrySet()) {
			String v = en.getValue();
			EntityField entityField = FieldUtil.getProperty(clazz, v);
			if (entityField == null)
				continue;
			// 获取相应属性的值
			Method method = entityField.getGetter();
			Object res = null;
			try {
				res = method.invoke(obj);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				log.error(e.getMessage(), e);
			}
			params.put(en.getKey(), res);
		}
		return params;
	}

	private synchronized static Map<String, Object> getTransientGetterParams(
            Map<String, EntityTable.TransientColumn> transientProperties, Object obj) {
		Map<String, String> properties = new HashMap<>();
		for (Map.Entry<String, EntityTable.TransientColumn> entry : transientProperties
				.entrySet()) {
			properties.put(entry.getKey(), entry.getValue().getQuondamName());
		}
		return getGetterParams(properties, obj);

	}

	public static EntityTable getEntityTableWithoutParams(Object obj) {
		Class<?> entityClass = obj.getClass();
		EntityTable entityTable = entityTableMap.get(entityClass);
		if (entityTable == null)
			initEntityNameMap(entityClass);
		entityTable = entityTableMap.get(entityClass);
		return entityTable;
	}

	// 手动设置根据查询的条件字段(暂未使用)
	public static EntityTable setColumnsForSelect(List<String> selectColumns,
			EntityTable entityTable) {
		entityTable.setIsSelectByPrimaryKey(false);
		entityTable.setSelectColumns(selectColumns);
		return entityTable;
	}

	/**
	 * 初始化实体属性
	 *
	 * @param entityClass
	 */
	private synchronized static void initEntityNameMap(Class<?> entityClass) {
		if (entityTableMap.get(entityClass) != null)
			return;
		Style style = Style.camelhumpAndLowercase;// 默认驼峰命名法
		// style，该注解优先于全局配置
		if (entityClass.isAnnotationPresent(NameStyle.class))
			style = entityClass.getAnnotation(NameStyle.class).value();

		// 创建并缓存EntityTable
		EntityTable entityTable = null;
		if (entityClass.isAnnotationPresent(Table.class)) {
			Table table = entityClass.getAnnotation(Table.class);
			if (!table.name().equals("")) {
				entityTable = new EntityTable(entityClass);
				entityTable.setTableName(table.name());
			}

		}
		if (entityTable == null) {
			entityTable = new EntityTable(entityClass);
			// 可以通过stye控制
			entityTable.setTableName(StyleUtil
					.convertByStyle(entityClass.getSimpleName(), style));
		}

		entityTable.setStyle(style);

		// 处理所有列
		List<EntityField> fields = null;
		fields = FieldUtil.getFields(entityClass);
		try {
			for (EntityField field : fields) {
				entityTable = processField(entityTable, style, field);
			}
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			log.error(e.getMessage(), e);
		}
		if (entityTable.getPrimaryKeys() == null
				|| entityTable.getPrimaryKeys().size() == 0)
			entityTable.setIsSelectByPrimaryKey(true);
		else
			entityTable.setIsSelectByPrimaryKey(false);

		// Map<String, String> allProperties = null;

		// if (entityTable.getProperties() != null)
		// allProperties = new HashMap<>(entityTable.getProperties());
		// if (entityTable.getTransientProperties() != null) {
		// if (allProperties == null)
		// allProperties = new HashMap<>(
		// entityTable.getTransientProperties());
		// else
		// allProperties.putAll(entityTable.getTransientProperties());
		// }
		// if (allProperties != null)
		// entityTable.setAllProperties(allProperties);

		entityTableMap.put(entityClass, entityTable);
	}

	/**
	 * 处理一列,最后输出顺序Transient.class>com.wehealth.weiyu.common.core.annotation.Column.class>Column.class、Id.class>NameStyle.class>
	 *
	 * @param entityTable
	 * @param style
	 * @param field
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private static EntityTable processField(EntityTable entityTable,
			Style style, EntityField field) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		String columnName = field.getName();
		// 排除字段
		if (field.isAnnotationPresent(javax.persistence.Transient.class)) {
			// 将忽略的字段放进transientParams中;
			Map<String, EntityTable.TransientColumn> transientProperties = entityTable
					.getTransientProperties();
			transientProperties = transientProperties == null
					? new HashMap<String, EntityTable.TransientColumn>()
					: transientProperties;

			if (field.isAnnotationPresent(Column.class)) {
				Column column = field.getAnnotation(Column.class);
				columnName = column.name();
			}
			if (field.isAnnotationPresent(
					com.apiserver.data.annotation.Column.class)) {
				com.apiserver.data.annotation.Column column = field
						.getAnnotation(
								com.apiserver.data.annotation.Column.class);
				columnName = (StringUtils.isNotEmpty(column.colName()))
						? column.colName()
						: columnName;
			}
			EntityTable.TransientColumn transientColumn = EntityTable.TransientColumn.builder()
					.columnName(columnName).quondamName(field.getName())
					.build();

			transientProperties.put(
					StyleUtil.convertByStyle(field.getName(), style),
					transientColumn);
			entityTable.setTransientProperties(transientProperties);

			return entityTable;
		}

		columnName = StyleUtil.convertByStyle(field.getName(), style);

		if (field.isAnnotationPresent(Column.class)) {
			Column column = field.getAnnotation(Column.class);
			columnName = column.name();
		}
		// Id
		if (field.isAnnotationPresent(Id.class)) {
			List<String> primaryKeys = entityTable.getPrimaryKeys();
			primaryKeys = primaryKeys == null
					? new ArrayList<String>()
					: primaryKeys;
			primaryKeys.add(columnName);
			entityTable.setPrimaryKeys(primaryKeys);
		}
		////////////////////////////////// 自定义的cloumn.class
		if (field.isAnnotationPresent(
				com.apiserver.data.annotation.Column.class)) {
			com.apiserver.data.annotation.Column column = field
					.getAnnotation(
							com.apiserver.data.annotation.Column.class);
			columnName = (StringUtils.isNotEmpty(column.colName()))
					? column.colName()
					: columnName;
			if (column.id())// 主键
			{
				List<String> primaryKeys = entityTable.getPrimaryKeys();
				primaryKeys = primaryKeys == null
						? new ArrayList<String>()
						: primaryKeys;
				primaryKeys.add(columnName);
				entityTable.setPrimaryKeys(primaryKeys);
//				if (column.idCreator() != null)
//					entityTable.setIdStrategy(column.idCreator());// 主键生成策略
//				if (column.rootIdLength() > 0)
//					entityTable.setRootIdLength(column.rootIdLength());// 主键生成策略
			}

		}
		////////////////////////////////
		// Column
		List<String> columns = entityTable.getColumns();
		Map<String, String> properties = entityTable.getProperties();

		if (columns == null)
			columns = new ArrayList<>();
		if (properties == null)
			properties = new HashMap<>();

		columns.add(columnName);
		properties.put(columnName, field.getName());
		entityTable.setColumns(columns);
		entityTable.setProperties(properties);

		return entityTable;

	}

}
