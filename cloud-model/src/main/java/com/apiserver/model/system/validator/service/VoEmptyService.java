package com.apiserver.model.system.validator.service;

import com.apiserver.model.system.validator.VoEmptyValidator;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class VoEmptyService implements ConstraintValidator<VoEmptyValidator,Object> {
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if(null == o){
            return false;
        }
        return true;
    }
}
