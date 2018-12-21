package com.apiserver.producer.system.service.impl;

import com.apiserver.data.service.BaseService;
import com.apiserver.kernel.core.AbstractMapper;
import com.apiserver.kernel.core.AbstractServiceImpl;

import com.apiserver.model.system.entity.SUserLog;
import com.apiserver.model.system.entity.SUsers;
import com.apiserver.producer.system.mapper.UserLogMapper;
import com.apiserver.producer.system.mapper.UsersMapper;
import com.apiserver.producer.system.service.UsersService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author jarvis
 * @date 2018-10-15
 */
@Repository
public class UsersServiceImpl extends BaseService<UsersMapper,SUsers> implements UsersService {

    @Resource
    private UsersMapper usersMapper;




//    @Override
//    public Users loginWeb(String account) {
//        return usersMapper.loginWeb(account);
//    }
//
//    @Override
//    public List<Users> selectAll(Users users) {
//        return usersMapper.selectAll();
//    }
//
//    @Override
//    public List<Map<String, String>> selectUserList(Map<String, Object> params) {
//        return usersMapper.selectUserList(params);
//    }


}
