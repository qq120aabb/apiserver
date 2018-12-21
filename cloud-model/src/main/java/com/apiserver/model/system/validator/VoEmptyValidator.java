package com.apiserver.model.system.validator;


import com.apiserver.model.system.validator.service.VoEmptyService;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR})  //注解作用域
@Retention(RetentionPolicy.RUNTIME)  //注解作用时间
@Constraint(validatedBy = VoEmptyService.class) //执行校验逻辑的类
public @interface VoEmptyValidator {

    //校验不过时候的信息
    String message() default "{org.hibernate.validator.constraints.NotBlank.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
