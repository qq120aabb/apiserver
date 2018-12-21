package com.apiserver.producer.system.service.impl;

import com.apiserver.data.service.BaseService;
import com.apiserver.model.system.entity.SErrorLog;
import com.apiserver.producer.system.mapper.ErrorLogMapper;
import com.apiserver.producer.system.service.ErrorLogService;
import org.springframework.stereotype.Repository;

/**
 * @author jarvis
 */
@Repository
public class ErrorLogServiceImpl  extends BaseService<ErrorLogMapper, SErrorLog> implements ErrorLogService {


}
