package com.apiserver.producer.system.service;

import com.apiserver.data.service.SqlService;
import com.apiserver.model.system.entity.SUserLog;

import java.util.List;
import java.util.Map;

/**
 * @author jarvis
 * @date 2018-10-25
 */
public interface UserLogService  extends SqlService<SUserLog> {

    List<SUserLog> userLogSelectAll(Map<String,Object> sUserLogMap);
}
