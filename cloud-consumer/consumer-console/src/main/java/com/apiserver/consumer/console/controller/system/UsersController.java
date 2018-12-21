package com.apiserver.consumer.console.controller.system;


import com.apiserver.consumer.console.api.system.UsersInterface;
import com.apiserver.kernel.result.JsonResult;
import com.apiserver.kernel.result.StatusCode;
import com.apiserver.kernel.utils.TokenGenerator;
import com.apiserver.model.system.entity.SUsers;
import com.apiserver.model.system.response.PageInfo;
import com.apiserver.model.system.response.SUsersResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jarvis
 * @date 2018-10-15
 */
@Api(description = "用户信息管理", tags = "UsersController", basePath = "/users")
@RestController
@RequestMapping(value = "/v1/users",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UsersController {

    public static final String token = "token";


    @Resource
    UsersInterface usersInterface;


    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "根据用户ID，查询用户的详细信息", notes = "返回单个用户的信息,用户的详细信息")
    @RequestMapping(value = "get", method = RequestMethod.GET)
    public JsonResult<SUsers> get(@RequestParam(value = "id") Long id) {
        return JsonResult.getSuccessResult(usersInterface.get(id));
    }
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "根据用户ID，查询用户指定的详细信息", notes = "返回单个指定用户的信息,指定用户的详细信息")
    @RequestMapping(value = "getUserInfo", method = RequestMethod.GET)
    public JsonResult<SUsersResult> getUserInfo(ServletRequest request) {
        HttpServletRequest  httpServletRequest =(HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("Authorization");
        Long id= TokenGenerator.getUserId(authorization);
        SUsers sUsers= usersInterface.get(id);
        if (sUsers!=null){
            SUsersResult sUsersResult=new SUsersResult();
            BeanUtils.copyProperties(sUsers,sUsersResult);
            return JsonResult.getSuccessResult(sUsersResult);
        }else{
            return JsonResult.getFailureResult(StatusCode.NonExistentUser, null);
        }
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "根据查询的参数，查询用户的列表信息", notes = "返回用户的列表信息。")
    @RequestMapping(value = "getAll", method = RequestMethod.GET)
    public JsonResult<PageInfo<SUsers>> getAll(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                               @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                               @RequestParam(value = "account",required = false) String account,
                                               @RequestParam(value = "mobile",required = false) String mobile,
                                               @RequestParam(value = "email",required = false) String email,
                                               @RequestParam(value = "status",required = false) Integer status,@RequestParam(value = "loginType",required = false) Integer loginType) {

        return JsonResult.getSuccessResult(usersInterface.getAll(pageNum,pageSize,account,mobile,email,status,loginType));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "修改密码", notes = "修改用户密码")
    @RequestMapping(value = "changePassword", method = RequestMethod.POST)
    public JsonResult<HashMap<String, Object>> changePassword(ServletRequest request, @RequestParam("userPwd") String userPwd,
                                              @RequestParam("pwd") String pwd){
        HttpServletRequest  httpServletRequest =(HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("Authorization");
        Long id= TokenGenerator.getUserId(authorization);
        if(authorization!=null){
           HashMap<String, Object> resultMap= usersInterface.changePassword(id,userPwd,pwd);
            if (MapUtils.isNotEmpty(resultMap)) {
                return JsonResult.getSuccessResult(resultMap);
            } else {
                return JsonResult.getFailureResult(StatusCode.INVALID_REQUEST, resultMap);
            }
        }else{
            return  JsonResult.getFailureResult(StatusCode.NotAuthorization, null);
        }
    }
    @ApiOperation(value = "用户登录", notes = "用户登录，可使用账户，邮件地址，手机号进行登录")
    @RequestMapping(value = "loginWeb", method = RequestMethod.GET)
    public JsonResult<HashMap<String, Object>> loginWeb(@RequestParam("account") String account,
                                                        @RequestParam("pwd") String pwd) {

        HashMap<String, Object> user = usersInterface.loginWeb(account, pwd);
        if (user.get(token) != null) {
            return JsonResult.getSuccessResult(user);

        } else {
            return JsonResult.getFailureResult(StatusCode.INVALID_REQUEST, user);
        }
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "保存用户的信息", notes = "保存用户的信息")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public JsonResult<Map<String,Object>> save(@RequestBody @Valid SUsers users) {
        Map<String, Object> resultMap = usersInterface.save(users);
        if(MapUtils.isNotEmpty(resultMap)){
            return JsonResult.getFailureResult(StatusCode.INVALID_REQUEST, resultMap);
        }
        return JsonResult.getSuccessResult();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "修改用户的信息", notes = "修改用户的信息")
    @RequestMapping(value = "edite", method = RequestMethod.POST)
    public JsonResult<Map<String,Object>> edite(ServletRequest request,@RequestBody SUsers usersEntity) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("Authorization");
        if (null != authorization) {
            Long userId = TokenGenerator.getUserId(authorization);
            usersEntity.setModifyId(String.valueOf(userId));
        }
        Map<String, Object> resultMap = usersInterface.edite(usersEntity);
        if(MapUtils.isNotEmpty(resultMap)){
            return JsonResult.getFailureResult(StatusCode.INVALID_REQUEST, resultMap);
        }
        return JsonResult.getSuccessResult();
    }
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "删除用户的信息", notes = "根据用户的ID,删除数据")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public JsonResult<Integer> delete(@RequestBody Long id) {
        return JsonResult.getSuccessResult(usersInterface.delete(id));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "检查是否存在用户的信息", notes = "根据用户的account,mobile,email检查是否存在账户")
    @RequestMapping(value = "checkExistUser", method = RequestMethod.GET)
    public JsonResult<Map<String, Object>> checkExistUser(
                                                          @RequestParam(value = "userId", required = false) Long userId,
                                                          @RequestParam(value = "account", required = false) String account,
                                                          @RequestParam(value = "mobile", required = false) String mobile,
                                                          @RequestParam(value = "email", required = false) String email) {
        Map<String, Object> resultMap = usersInterface.checkExistUser(userId,account, mobile, email);
        if (MapUtils.isNotEmpty(resultMap)) {
            return JsonResult.getFailureResult(StatusCode.INVALID_REQUEST, resultMap);
        }
        return JsonResult.getSuccessResult();
    }

}