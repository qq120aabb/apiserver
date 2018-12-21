package com.apiserver.consumer.console.api.system;


import com.apiserver.model.system.entity.SUsers;
import com.apiserver.model.system.response.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jarvis
 * @date 2018-11-12
 */
@FeignClient(name = "producer-system/v1/users")
public interface UsersInterface {


    /**
     * 根据id 查询用户信息
     * @param id
     * @return
     */
    @RequestMapping(value = "get",method = RequestMethod.GET)
    SUsers get(@RequestParam(value = "id") Long id);


    /**
     *
     * @param account
     * @param pwd
     * @return
     */
    @RequestMapping(value = "loginWeb", method = RequestMethod.GET)
    HashMap<String,Object> loginWeb(@RequestParam(value = "account") String account,
                                                @RequestParam(value = "pwd") String pwd);

    /**
     *修改用户密码
     * @param id
     * @param userPwd
     * @param pwd
     * @return
     */
    @RequestMapping(value = "changePassword", method = RequestMethod.POST)
    HashMap<String, Object>changePassword(@RequestParam(value = "id") Long id,
                                    @RequestParam(value = "userPwd") String  userPwd,
                                    @RequestParam(value = "pwd") String pwd);
    /**
     * 查询所有分页信息
     * @param pageNum
     * @param pageSize
     * @param account
     * @param mobile
     * @param email
     * @param status
     * @param loginType
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.GET)
    PageInfo<SUsers> getAll(@RequestParam(value = "pageNum") int pageNum,@RequestParam(value = "pageSize") int pageSize,
                            @RequestParam(value = "account",required = false) String account,@RequestParam(value = "mobile",required = false) String mobile,
                            @RequestParam(value = "email",required = false) String email,@RequestParam(value = "status",required = false) Integer status,@RequestParam(value = "loginType",required = false) Integer loginType);


    /**
     * 保存用户信息
     * @param usersEntity
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    Map<String,Object> save(@RequestBody SUsers usersEntity);

    /**
     * 修改用户的信息
     * @param usersEntity
     * @return
     */
    @ApiOperation(value = "修改用户的信息", notes = "修改用户的信息")
    @RequestMapping(value = "edite", method = RequestMethod.POST)
     Map<String,Object> edite(@RequestBody SUsers usersEntity) ;

    /**
     * 根据Id 删除信息
     * @param id
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    Integer delete(@RequestBody Long id);

    @RequestMapping(value = "checkExistUser", method = RequestMethod.GET)
     Map<String, Object> checkExistUser(@RequestParam(value = "userId", required = false) Long userId,@RequestParam(value = "account", required = false) String account, @RequestParam(value = "mobile", required = false) String mobile, @RequestParam(value = "email", required = false) String email);
}
