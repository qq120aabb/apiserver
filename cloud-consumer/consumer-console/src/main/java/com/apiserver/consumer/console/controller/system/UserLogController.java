package com.apiserver.consumer.console.controller.system;

import com.apiserver.consumer.console.api.system.UsersLogInterface;
import com.apiserver.kernel.result.JsonResult;
import com.apiserver.model.system.entity.SUserLog;
import com.apiserver.model.system.response.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
/**
 * @author liulei
 * @date 2018-12-6
 */
@Api(description = "用户日志管理", tags = "UserLogController", basePath = "/userLog")
@RestController
@RequestMapping(value = "/v1/userLog",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserLogController  {
    @Resource
    UsersLogInterface usersLogInterface;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token" , required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "根据查询的参数，查询用户日志的列表信息", notes = "返回用户的日志列表信息")
    @RequestMapping(value = "getAll", method = RequestMethod.GET)
    public JsonResult<PageInfo<SUserLog>> getAll(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                 @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                                 @RequestParam(value = "account",required = false)String  account,
                                                 @RequestParam(value = "userIp",required = false)String userIp,
                                                 @RequestParam(value = "startTime",required = false)String  startTime,
                                                 @RequestParam(value = "endTime",required = false)String  endTime,
                                                 @RequestParam(value = "successFlag",required = false)Integer successFlag) {
        PageInfo<SUserLog> pageResult=  usersLogInterface.getAll(pageNum,pageSize,account,userIp,startTime,endTime,successFlag);
        return JsonResult.getSuccessResult(pageResult);
    }



}
