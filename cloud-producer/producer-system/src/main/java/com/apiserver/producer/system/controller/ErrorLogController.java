package com.apiserver.producer.system.controller;


import com.apiserver.producer.system.service.ErrorLogService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jarvis
 * @date 2018-10-20
 */

@Api(description = "系统错误日志管理", tags = "ErrorLogController", basePath = "/errorLog")
@RestController
@RequestMapping(value = "/v1/errorLog",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ErrorLogController {

    @Autowired
    ErrorLogService errorLogService;

}
