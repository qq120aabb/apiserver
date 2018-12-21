/**
 * @title: DynaSqlProvider.java
 * @package com.wehealth.weiyu.common.data.mybatis
 * @description: TODO
 * @author hushsh
 * @date 2016年12月30日下午4:59:00
 * @version V1.0
 */

package com.apiserver.data.mybatis;


import com.apiserver.data.mybatis.model.EntityTable;
import com.apiserver.data.mybatis.util.EntityUtil;
import com.apiserver.data.mybatis.util.StyleUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;


import java.util.*;

/**
 * @description 生成mybatis动态sql
 * @author hs
 * @date 2016年12月30日下午4:59:00
 */
@Slf4j
public class SqlProvider {

	/**
	 * 
	 * @param t
	 *            实体类
	 * @param propertyNameList
	 *            需要查询的字段,查询字段的必须包含@Transient注解,并且需要加上对应的@Column注解说明该字段是对应数据库那个字段
	 * @return
	 */
	public <T> String selectAllByOtherKeys(final T t,
			final List<String> propertyNameList) {
		if (propertyNameList == null || propertyNameList.size() == 0)
			return "";
		EntityTable entity = EntityUtil.getEntityTable(t);
		List<String> existPropertyNameList = new ArrayList<>();
		for (String propertyName : propertyNameList) {
			propertyName = StyleUtil.convertByStyle(propertyName,
					entity.getStyle());
			if (entity.getTransientParams().containsKey(propertyName))
				existPropertyNameList.add(propertyName);
		}

		SQL sql = new SQL().SELECT(StringUtils.join(entity.getColumns(), ","))
				.FROM(entity.getTableName()).WHERE("1 = 1");
		for (Map.Entry<String, Object> entry : entity.getParams().entrySet()) {
			Object val = entry.getValue();
			if (val != null) {
				if (val instanceof String)
					val = disposeStr(val.toString());
				if (val instanceof Boolean)
					val = disposeBoolean(Boolean.valueOf(val.toString()));
				sql = sql.WHERE(entry.getKey() + "='" + val + "'");
			}
		}

		if (existPropertyNameList.size() > 0) {
			for (String epn : existPropertyNameList) {
				EntityTable.TransientColumn tc = entity.getTransientProperties().get(epn);
				Object val = entity.getTransientParams().get(epn);
				if (val instanceof String)
					val = disposeStr(val.toString());
				if (val instanceof Boolean)
					val = disposeBoolean(Boolean.valueOf(val.toString()));
				if (val instanceof List) {
					sql = sql.WHERE(tc.getColumnName() + " in ("
							+ disposeList((List<?>) val) + ")");
				} else
					sql = sql.WHERE(tc.getColumnName() + "='" + val + "'");
			}
		}

		log.debug(">>>>selectAllByOtherKeys.sql:[{}]", sql.toString());
		return sql.toString();
	}

	/**
	 * 
	 *
	 *        t    实体类
	 *
	 *       likePropertyNameList     需要like查询的字段,查询字段的不能包含@Transient注解
	 * @return
	 */
	public <T> String selectAllLike(Map<String,Object> param) {
		T t=(T)	param.get("t");
		List<String> likePropertyNameList=(List<String>)param.get("likePropertyNameList");
		LinkedHashMap<String,Object> orderMap=(LinkedHashMap<String,Object>)param.get("orderMap");


		EntityTable entity = EntityUtil.getEntityTable(t);

		List<String> existPropertyNameList = new ArrayList<>();
		for (String propertyName : likePropertyNameList) {
			propertyName = StyleUtil.convertByStyle(propertyName,
					entity.getStyle());
			if (entity.getParams().containsKey(propertyName))
				existPropertyNameList.add(propertyName);
		}


		SQL sql = new SQL().SELECT(StringUtils.join(entity.getColumns(), ","))
				.FROM(entity.getTableName()).WHERE("1 = 1");
		for (Map.Entry<String, Object> entry : entity.getParams().entrySet()) {
			Object val = entry.getValue();
			if (val != null) {
				if (val instanceof String)
					val = disposeStr(val.toString());
				if (val instanceof Boolean)
					val = disposeBoolean(Boolean.valueOf(val.toString()));
				if (existPropertyNameList.contains(entry.getKey()))
					sql = sql.WHERE(entry.getKey() + " like '%" + val + "%'");
				else
					sql = sql.WHERE(entry.getKey() + "='" + val + "'");



			}

		}

		if(null !=orderMap){
			for(Map.Entry<String, Object> orderEntry : orderMap.entrySet()){
				String propertyName = StyleUtil.convertByStyle(orderEntry.getKey(),entity.getStyle());
				if (entity.getParams().containsKey(propertyName))
					sql=sql.ORDER_BY(propertyName+" "+orderEntry.getValue());
			}
		}


		log.debug(">>>>selectAllLike.sql:[{}]", sql.toString());
		return sql.toString();
	}

	/**
	 * 
	 * @description select语句所有查询条件均为等于号
	 * @author hushsh
	 * @date 2017年1月3日上午10:36:00
	 * @param t
	 * @return
	 */
	public <T> String selectAll(final T t) {
		EntityTable entity = EntityUtil.getEntityTable(t);
		SQL sql = new SQL().SELECT(StringUtils.join(entity.getColumns(), ","))
				.FROM(entity.getTableName()).WHERE("1 = 1");
		for (Map.Entry<String, Object> entry : entity.getParams().entrySet()) {
			if (entry.getValue() != null)
				sql = sql.WHERE(entry.getKey() + "=#{"
						+ entity.getProperties().get(entry.getKey()) + "}");
		}
		log.debug(">>>>selectAll.sql:[{}]", sql.toString());
		return sql.toString();
	}

	/**
	 * 
	 * @description 统一查询语句,根据主键查询，所有查询条件均为等于号
	 * @author hushsh
	 * @date 2017年1月3日上午10:42:29
	 * @param t
	 * @return
	 */
	public <T> String selectByPrimaryKey(final T t) {
		EntityTable entity = EntityUtil.getEntityTable(t);
		SQL sql = new SQL().SELECT(StringUtils.join(entity.getColumns(), ","))
				.FROM(entity.getTableName()).WHERE("1 = 1");
		for (String primaryKey : entity.getPrimaryKeys()) {
			if (primaryKey != null)
				sql = sql.WHERE(primaryKey + "=#{"
						+ entity.getProperties().get(primaryKey) + "}");
		}

		log.debug(">>>>selectByPrimaryKey.sql:[{}]", sql.toString());
		return sql.toString();
	}

	/**
	 * 
	 * @description 通用添加语句,只添加有值的字段
	 * @author hushsh
	 * @date 2017年1月3日上午10:42:07
	 * @param t
	 * @return
	 */
	public <T> String insert(final T t) {
		EntityTable entity = EntityUtil.getEntityTable(t);

		SQL sql = new SQL().INSERT_INTO(entity.getTableName());
		for (Map.Entry<String, Object> entry : entity.getParams().entrySet()) {
			if (entry.getValue() != null)
				sql = sql.VALUES(entry.getKey(), "#{"
						+ entity.getProperties().get(entry.getKey()) + "}");
		}
		log.debug(">>>>insert.sql:[{}]", sql.toString());
		return sql.toString();
	}

	/**
	 * 
	 * @description 通用修改语句，只修改有值的主键字段
	 * @author hushsh
	 * @date 2017年1月3日上午10:56:36
	 * @param t
	 * @return
	 */
	public <T> String updateByPrimaryKey(final T t) {
		EntityTable entity = EntityUtil.getEntityTable(t);

		SQL sql = new SQL().UPDATE(entity.getTableName());
		StringBuilder setSql = new StringBuilder();
		for (Map.Entry<String, Object> en : entity.getParams().entrySet()) {
			if (en.getValue() != null
					&& !entity.getPrimaryKeys().contains(en.getKey()))
				setSql.append(en.getKey() + "=#{"
						+ entity.getProperties().get(en.getKey()) + "} " + ",");
		}
		sql = sql.SET(
				setSql.toString().substring(0, setSql.toString().length() - 1));
		for (String primaryKey : entity.getPrimaryKeys()) {
			if (primaryKey != null)
				sql = sql.WHERE(primaryKey + "=#{"
						+ entity.getProperties().get(primaryKey) + "}");
		}

		log.debug(">>>>updateByPrimaryKey.sql:[{}]", sql.toString());
		return sql.toString();
	}

	public <T> String updateByOtherKeys(final T t,
			List<String> propertyNameList) {
		EntityTable entity = EntityUtil.getEntityTable(t);
		SQL sql = new SQL().UPDATE(entity.getTableName());
		StringBuilder setSql = new StringBuilder();

		for (Map.Entry<String, Object> en : entity.getParams().entrySet()) {
			if (en.getValue() != null && !propertyNameList
					.contains(entity.getProperties().get(en.getKey()))) {
				Object val = en.getValue();

				if (val instanceof List) {
					@SuppressWarnings("unchecked")
					String _val = disposeList((List<Object>) val);
					setSql.append(en.getKey() + " in (" + _val + "),");

				} else {
					if (val instanceof String)
						val = disposeStr(val.toString());
					if (val instanceof Boolean)
						val = disposeBoolean(Boolean.valueOf(val.toString()));
					setSql.append(en.getKey() + "='" + val + "',");
				}

			}

		}
		sql = sql.SET(
				setSql.toString().substring(0, setSql.toString().length() - 1));
		sql = sql.WHERE("1=1");
		boolean isExist = false;
		for (String propertyName : propertyNameList) {
			propertyName = StyleUtil.convertByStyle(propertyName,
					entity.getStyle());
			String selColumn = getSelectColumn(entity, propertyName);
			Object columnVal = getSelectColumnVal(entity, propertyName);
			if (StringUtils.isNotEmpty(selColumn)) {
				if (columnVal instanceof List) {
					columnVal = disposeList((List<?>) columnVal);
					sql = sql.AND()
							.WHERE(selColumn + " in (" + columnVal + ")");
				} else
					sql = sql.AND().WHERE(selColumn + "='" + columnVal + "'");

				isExist = true;
			}
		}

		log.debug(">>>>updateByOtherKeys.sql:[{}]", sql.toString());
		if (isExist)
			return sql.toString();
		else
			return "";
	}

	private String getSelectColumn(EntityTable entity, String propertyName) {
		if (entity.getParams().containsKey(propertyName))
			return propertyName;
		if (entity.getTransientParams().containsKey(propertyName)) {
			EntityTable.TransientColumn trCol = entity.getTransientProperties()
					.get(propertyName);
			return trCol.getColumnName();
		}
		return null;
	}

	private Object getSelectColumnVal(EntityTable entity, String propertyName) {
		Object res = null;
		if (entity.getParams().containsKey(propertyName))
			res = entity.getParams().get(propertyName);
		if (entity.getTransientParams().containsKey(propertyName))
			res = entity.getTransientParams().get(propertyName);
		if (res instanceof String)
			res = disposeStr(res.toString());
		return res;
	}

	/**
	 * 
	 * @description 通用删除语句，只修改有值的主键字段
	 * @author hushsh
	 * @date 2017年1月3日上午11:04:14
	 * @param t
	 * @return
	 */
	public <T> String deleteByPrimaryKey(final T t) {
		EntityTable entity = EntityUtil.getEntityTable(t);
		SQL sql = new SQL().DELETE_FROM(entity.getTableName()).WHERE("1=1");
		for (String primaryKey : entity.getPrimaryKeys()) {
			if (primaryKey != null)
				sql = sql.WHERE(primaryKey + "=#{"
						+ entity.getProperties().get(primaryKey) + "}");
		}
		log.debug(">>>>deleteByPrimaryKey.sql:[{}]", sql.toString());
		return sql.toString();
	}

	/**
	 * 
	 * @description 删除语句，根据传进来的有值参数进行删除
	 * @author hushsh
	 * @date 2017年1月3日上午11:05:54
	 * @param t
	 * @return
	 */
	public <T> String delete(final T t) {
		EntityTable entity = EntityUtil.getEntityTable(t);
		SQL sql = new SQL().DELETE_FROM(entity.getTableName()).WHERE("1=1");
		for (Map.Entry<String, Object> en : entity.getParams().entrySet()) {
			if (en.getValue() != null)
				sql = sql.WHERE(en.getKey() + "=#{"
						+ entity.getProperties().get(en.getKey()) + "} ");
		}
		log.debug(">>>>delete.sql:[{}]", sql.toString());
		return sql.toString();
	}

	/**
	 * 
	 * @description 获取数据库中最大的主键id，对于表中有多个主键id的，该方法无效
	 * @author hushsh
	 * @date 2017年1月3日上午11:14:33
	 * @param t
	 * @return
	 */
	public <T> String getMaxPrimaryKey(final T t) {
		EntityTable entity = EntityUtil.getEntityTable(t);
		String primaryKey = entity.getPrimaryKeys().get(0);// 只获取第一个
		SQL sql = new SQL().SELECT("max(" + primaryKey + ")")
				.FROM(entity.getTableName());
		String column = null;
		if ((column = entity.getParentColumnName(entity)) != null)
			sql = sql.WHERE(
					column + "=#{" + entity.getProperties().get(column) + "}");

		log.debug(">>>>getMaxPrimaryKey.sql:[{}]", sql.toString());
		return sql.toString();
	}

	/**
	 * 
	 * @description 获取数据库中最大的主键id，将数据库主键id强制转换成整形再取最大值，对于表中有多个主键id的，该方法无效
	 * @author hushsh
	 * @date 2017年1月3日上午11:14:33
	 * @param t
	 * @return
	 */
	public <T> String getMaxPrimaryKeyToNumber(final T t) {
		EntityTable entity = EntityUtil.getEntityTable(t);
		String primaryKey = entity.getPrimaryKeys().get(0);// 只获取第一个
		SQL sql = new SQL().SELECT("max(to_number(" + primaryKey + "))")
				.FROM(entity.getTableName());
		String column = null;
		if ((column = entity.getParentColumnName(entity)) != null)
			sql = sql.WHERE(
					column + "=#{" + entity.getProperties().get(column) + "}");

		log.debug(">>>>getMaxPrimaryKey.sql:[{}]", sql.toString());
		return sql.toString();
	}

	private String disposeStr(String val) {
		return val.replaceAll("'", "\"");
	}

	private int disposeBoolean(Boolean val) {
		if (val)
			return 1;
		else
			return 0;
	}

	private <T> String disposeList(List<T> vals) {
		StringBuilder sb = new StringBuilder();
		for (T t : vals) {
			sb.append("\'").append(t.toString()).append("\',");
		}

		return sb.toString().substring(0, sb.toString().length() - 1);
	}

	public static void main(String[] args) {
		List<String> vals = Arrays.asList("1", "2 or 1 = 1", "3", "4 --");
		SqlProvider sp = new SqlProvider();
		System.out.println(sp.disposeList(vals));
	}

}
