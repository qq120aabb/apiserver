package com.apiserver.producer.system.mapper;

import com.apiserver.data.dao.BaseMapper;
import com.apiserver.model.system.entity.SUserLog;

import java.util.List;
import java.util.Map;

/**
 * @author jarvis
 */
public interface UserLogMapper extends BaseMapper<SUserLog>  {
     List<SUserLog> userLogSelectAll(Map<String,Object> sUserLogMap);
}