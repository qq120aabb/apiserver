package com.apiserver.producer.system.controller;


import com.apiserver.model.system.entity.SUserLog;
import com.apiserver.producer.system.service.UserLogService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jarvis
 * @date 2018-10-20
 */
@Api(description = "用户日志管理", tags = "UserLogController", basePath = "/userLog")
@RestController
@RequestMapping(value = "/v1/userLog",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserLogController  {

    @Resource
    UserLogService userLogService;



    @ApiOperation(value = "查看所有日志", notes = "查看所有日志")
    @RequestMapping(value = "getAll",method = RequestMethod.GET)
    public PageInfo<SUserLog> getAll(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                     @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                     @RequestParam(value = "account",required = false)String account,
                                     @RequestParam(value = "userIp",required = false)String userIp,
                                     @RequestParam(value = "startTime",required = false)String  startTime,
                                     @RequestParam(value = "endTime",required = false)String  endTime,
                                     @RequestParam(value = "successFlag",required = false)String  successFlag) throws ParseException{
        PageHelper.startPage(pageNum, pageSize);
        Map<String,Object> sUserLogMap=new HashMap<>();
        sUserLogMap.put("account",account);
        sUserLogMap.put("userIp",userIp);
        sUserLogMap.put("startTime",startTime);
        sUserLogMap.put("endTime",endTime);
        sUserLogMap.put("successFlag",successFlag);
        List<SUserLog> sUserLogList = userLogService.userLogSelectAll(sUserLogMap);
        PageInfo<SUserLog> pageInfo = new PageInfo<>(sUserLogList);

        return  pageInfo;
    }
}
