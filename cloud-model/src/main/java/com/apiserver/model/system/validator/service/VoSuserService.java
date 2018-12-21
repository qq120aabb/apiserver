package com.apiserver.model.system.validator.service;

import com.apiserver.model.system.entity.SUsers;
import com.apiserver.model.system.validator.VoSuserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class VoSuserService implements ConstraintValidator<VoSuserValidator,Object> {
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        SUsers susers=(SUsers)o;
        if(null !=susers && StringUtils.isEmpty(susers.getAccount())&& StringUtils.isEmpty(susers.getMobile()) && StringUtils.isEmpty(susers.getEmail())){
            return false;
        }
        return true;
    }
}