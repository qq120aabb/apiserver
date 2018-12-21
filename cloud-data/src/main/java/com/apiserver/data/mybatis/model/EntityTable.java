package com.apiserver.data.mybatis.model;


import com.apiserver.data.annotation.Style;
import lombok.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EntityTable {

	private String tableName;

	private Class<?> entityClass;

	private List<String> primaryKeys;

	private List<String> columns;

	private List<String> selectColumns;

	private Boolean isSelectByPrimaryKey;

	private Map<String, String> properties;

	private Map<String, Object> params;

//	private IdStrategy idStrategy;

	private Integer rootIdLength;

	/** 20170116新增 **/
	private Map<String, TransientColumn> transientProperties;

	/** 20170116新增 **/
	private Map<String, Object> transientParams;

	/** 20170824新增 **/
	private Style style;

	private final static Map<String, String> PARENT_NAME_MAP = new ConcurrentHashMap<>();

	public EntityTable(Class<?> entityClass) {
		super();
		this.entityClass = entityClass;
	}
	
	

	public String getParentColumnName(EntityTable entity) {

		final String key = entity.getEntityClass() + "_PARENT_ID";
		// jdk7用法
		if (!PARENT_NAME_MAP.containsKey(key)) {
			for (String _columnName : entity.getColumns()) {
				if (_columnName.toLowerCase().startsWith("parent")) {
					PARENT_NAME_MAP.put(key, _columnName);
					break;
				}
			}
		}
		return PARENT_NAME_MAP.containsKey(key)
				? PARENT_NAME_MAP.get(key)
				: null;
		// List<String> columns = entity.getColumns().stream().filter(s ->
		// s.toLowerCase().startsWith("parent")).collect(Collectors.toList());//jdk8用法
	}
	public String getParentId(EntityTable entity) {
		String val = "0";
		String column = null;
		if ((column = entity.getParentColumnName(entity)) != null) {
			if (entity.getParams().containsKey(column)
					&& isNotEmpty(entity.getParams().get(column)))
				return (String) entity.getParams().get(column);
		}
		return val;
	}

	private boolean isNotEmpty(Object obj) {
		return obj != null && !(obj + "").isEmpty();
	}

	@Getter
	@Setter
	@Builder
	@ToString
	public static class TransientColumn {
		private String quondamName;
		private String columnName;
	}

	public static void main(String[] args)
			throws IllegalAccessException, InvocationTargetException {
		EntityTable en = new EntityTable();
//		BeanUtils.setProperty(en, "rootIdLength", "23");
		System.out.println(en.getRootIdLength());
	}

}
