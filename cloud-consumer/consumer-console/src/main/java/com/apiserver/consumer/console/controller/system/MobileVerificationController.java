package com.apiserver.consumer.console.controller.system;


import com.apiserver.consumer.console.api.system.MobileVerificationInterface;
import com.apiserver.kernel.result.JsonResult;
import com.apiserver.model.system.entity.SMobileVerification;
import com.apiserver.model.system.response.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author jarvis
 * @date 2018-10-20
 */
@Api(description = "系统统一短信管理", tags = "MobileVerificationController", basePath = "/mobileVerification")
@RestController
@RequestMapping(value = "/v1/mobileVerification",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MobileVerificationController {

    @Resource
    MobileVerificationInterface mobileVerificationInterface;


    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token" , required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "【后台】根据短信验证码的ID，查询验证码的详细信息", notes = "返回单个验证码的信息")
    @RequestMapping(value = "get",method = RequestMethod.GET)
    public JsonResult<SMobileVerification> get(@RequestParam(value = "id") Long id){
        return JsonResult.getSuccessResult(mobileVerificationInterface.get(id));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "【后台】根据查询的参数，查询短信日志的列表信息", notes = "返回短信日志的列表信息")
    @RequestMapping(value = "getAll", method = RequestMethod.GET)
    public JsonResult<PageInfo<SMobileVerification>> getAll(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                            @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                                            @RequestParam(value = "mobile",required = false) String mobile){
        return JsonResult.getSuccessResult(mobileVerificationInterface.getAll(pageNum,pageSize,mobile));
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "【移动端】根据用户的手机号发送验证码，并写入数据库表", notes = "返回是否发送成功的信息,用于注册的手机号验证码")
    @RequestMapping(value = "sendVerificationCode", method = RequestMethod.GET)
    public JsonResult<HashMap<String, Object>> sendVerificationCode(@RequestParam(value = "mobile") String mobile){

        return JsonResult.getSuccessResult(mobileVerificationInterface.sendVerificationCode(mobile));
    }



}
