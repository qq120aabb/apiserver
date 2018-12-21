package com.apiserver.producer.system.controller;

import com.apiserver.producer.system.service.EmailVerificationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jarvis
 * @date 2018-10-20
 */
@Api(description = "系统邮件管理", tags = "EmailVerificationController", basePath = "/emailVerification")
@RestController
@RequestMapping(value = "/v1/emailVerification",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class EmailVerificationController {

    @Autowired
    EmailVerificationService emailVerificationService;


}
