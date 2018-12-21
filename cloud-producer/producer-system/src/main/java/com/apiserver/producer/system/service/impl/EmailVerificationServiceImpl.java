package com.apiserver.producer.system.service.impl;

import com.apiserver.data.service.BaseService;
import com.apiserver.model.system.entity.SEmailVerification;
import com.apiserver.producer.system.mapper.EmailVerificationMapper;
import com.apiserver.producer.system.service.EmailVerificationService;
import org.springframework.stereotype.Repository;

@Repository
public class EmailVerificationServiceImpl  extends BaseService<EmailVerificationMapper, SEmailVerification> implements EmailVerificationService  {

}
