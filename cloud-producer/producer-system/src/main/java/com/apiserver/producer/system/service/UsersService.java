package com.apiserver.producer.system.service;

import com.apiserver.data.service.SqlService;
import com.apiserver.model.system.entity.SUsers;

/**
 * @author jarvis
 * @date 2018-10-25
 */
public interface UsersService extends  SqlService<SUsers> {

//    /**
//     * 通用登陆
//     * @param account
//     * @return
//     */
//    SUsers loginWeb(String account);
//
//    /**
//     * 后台查询用户信息列表
//     * @param users
//     * @return
//     */
//    List<SUsers> selectAll(SUsers users);
//
//
//    /**
//     * 根据条件查询用户
//     *
//     * @param params
//     * @return
//     */
//    List<Map<String, String>> selectUserList(Map<String, Object> params);
}
