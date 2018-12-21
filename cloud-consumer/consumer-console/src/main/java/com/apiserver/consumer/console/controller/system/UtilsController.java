package com.apiserver.consumer.console.controller.system;


import com.apiserver.consumer.console.config.QiNiuToken;
import com.apiserver.kernel.result.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author jarvis
 * @date 2018-10-15
 */
@Api(description = "公共工具SDK接入等", tags = "UtilsController", basePath = "/utils")
@RestController
@RequestMapping(value = "/v1/utils",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UtilsController {

    @Resource
    QiNiuToken qiNiuToken;


    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "获取七牛云图片存储的token", notes = "获取七牛云图片存储的token")
    @RequestMapping(value = "getQiNiuToken", method = RequestMethod.GET)
    public JsonResult<String> getQiNiuToken(){
        return JsonResult.getSuccessResult(qiNiuToken.getQiniuToken());
    }

}
