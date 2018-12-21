package com.apiserver.producer.system.service.impl;

import com.apiserver.data.service.BaseService;
import com.apiserver.model.system.entity.SUserLog;
import com.apiserver.producer.system.mapper.UserLogMapper;
import com.apiserver.producer.system.service.UserLogService;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository(value="userLogService")
public class UserLogServiceImpl  extends BaseService<UserLogMapper,SUserLog> implements UserLogService {
    @Override
    public List<SUserLog> userLogSelectAll(Map<String,Object> sUserLogMap) {

        List<SUserLog> userLogSelectAll=dao.userLogSelectAll(sUserLogMap);
        return userLogSelectAll;
    }
}
