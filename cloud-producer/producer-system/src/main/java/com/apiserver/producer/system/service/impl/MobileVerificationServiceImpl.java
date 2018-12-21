package com.apiserver.producer.system.service.impl;

import com.apiserver.data.service.BaseService;
import com.apiserver.model.system.entity.SMobileVerification;
import com.apiserver.producer.system.mapper.MobileVerificationMapper;
import com.apiserver.producer.system.service.MobileVerificationService;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jarvis
 */
@Repository
public class MobileVerificationServiceImpl  extends BaseService<MobileVerificationMapper, SMobileVerification> implements MobileVerificationService  {

}
