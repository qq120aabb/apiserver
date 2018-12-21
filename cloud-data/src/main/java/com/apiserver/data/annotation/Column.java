package com.apiserver.data.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Column {

	boolean id() default false;// 是否主键

	String colName() default ""; // 字段名称

	String jdbcType() default "";// jdbc类型

	IdStrategy idCreator() default IdStrategy.TIMESTAMP_CREATOR;// 主键生成策略

	int rootIdLength() default 0;// 根节点id的长度，不足的前面将会补0(树形id生成策略时需要设置)

	// String initValue() default "0";//初始值

	// String defaultValue() default "";//默认值

}
