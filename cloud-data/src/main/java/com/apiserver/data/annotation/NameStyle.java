package com.apiserver.data.annotation;

import java.lang.annotation.*;

/**
 * 名字转换样式，注解的优先级高于全局配置
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NameStyle {

	Style value() default Style.normal;

}
