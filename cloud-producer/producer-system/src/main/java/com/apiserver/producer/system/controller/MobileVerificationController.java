package com.apiserver.producer.system.controller;

import com.apiserver.kernel.result.StatusCode;
import com.apiserver.kernel.utils.DateUtils;
import com.apiserver.kernel.utils.RandomUtil;
import com.apiserver.model.system.entity.SMobileVerification;
import com.apiserver.model.system.entity.SRoles;
import com.apiserver.producer.system.service.MobileVerificationService;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author jarvis
 * @date 2018-10-20
 */
@Api(description = "系统短信管理", tags = "MobileVerificationController", basePath = "/mobileVerification")
@RestController
@RequestMapping(value = "/v1/mobileVerification",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MobileVerificationController {

    @Autowired
    MobileVerificationService mobileVerificationService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token" , required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "【后台】根据短信验证码的ID，查询验证码的详细信息", notes = "返回单个验证码的信息")
    @RequestMapping(value = "get",method = RequestMethod.GET)
    public SMobileVerification get(@RequestParam(value = "id") Long id){
        return mobileVerificationService.dynaSelectByPrimaryKey(SMobileVerification.builder().id(id).build());
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "【后台】根据查询的参数，查询短信日志的列表信息", notes = "返回短信日志的列表信息")
    @RequestMapping(value = "getAll", method = RequestMethod.GET)
    public PageInfo<SMobileVerification> getAll(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                                @RequestParam(value = "mobile",required = false) String mobile) {

        PageHelper.startPage(pageNum, pageSize);
        List<String> likeParam = new ArrayList<>();
        likeParam.add("mobile");
        LinkedHashMap<String,Object> orderMap = new LinkedHashMap<>();
        orderMap.put("createTime","desc");
        List<SMobileVerification> sRolesList = mobileVerificationService.dynaSelectAllLike(SMobileVerification.builder().mobile(mobile)
                .build(), likeParam,orderMap);

        PageInfo<SMobileVerification> pageInfo = new PageInfo<>(sRolesList);
        return pageInfo;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "【移动端】根据用户的手机号发送验证码，并写入数据库表", notes = "返回是否发送成功的信息,用于注册的手机号验证码")
    @RequestMapping(value = "sendVerificationCode", method = RequestMethod.GET)
    public HashMap<String, Object> sendVerificationCode(@RequestParam(value = "mobile") String mobile){

        Config config = ConfigService.getAppConfig();
        //每天请求短信限额5次,每次短信发送间隔60s
        Integer DefaultPerDayTime = 5;
        Integer DefaultPerSecond = 60;
        Integer perDayTime = config.getIntProperty("DefaultPerDayTime", DefaultPerDayTime);
        Integer perSecond = config.getIntProperty("DefaultPerSecond", DefaultPerSecond);

        HashMap<String,Object> codeMessage=new HashMap<>();

        boolean isSend=false;
        String beginTime=DateUtils.currentDate()+" 00:00:00";
        HashMap<String,Object> queryMap = new HashMap<>();
        queryMap.put("mobile",mobile);
        queryMap.put("beginTime",beginTime);
        HashMap<String,Object> orderMap = new HashMap<>();
        orderMap.put("createTime","desc");
        List<SMobileVerification> list=mobileVerificationService.fetchByParameters(queryMap,orderMap);

        if(list.size()>perDayTime){
            codeMessage.put("statusCode",StatusCode.smsExceedingLimit.getCode().toString());
            codeMessage.put("statusMessage",StatusCode.smsExceedingLimit.getMeaning());
            return codeMessage;
        }

        if(list.size()>0){
            SMobileVerification entity=list.stream().findFirst().get();

            Long from=DateUtils.getCurrentTime();
            Long to=entity.getCreateTime().getTime();

            Long mills = from - to;
            Long minutes=mills/1000;
            if(minutes > Long.valueOf(perSecond)){
                isSend=true;
            }
        }else {
            isSend=true;
        }

        if(isSend){
            sendMobileCode(mobile);
            codeMessage.put("statusCode",StatusCode.CREATED.getCode().toString());
            codeMessage.put("statusMessage",StatusCode.CREATED.getMeaning());
            return codeMessage;

        }else {
            codeMessage.put("statusCode",StatusCode.smsFailure.getCode().toString());
            codeMessage.put("statusMessage",StatusCode.smsFailure.getMeaning());
            return codeMessage;
        }
    }

    public Integer sendMobileCode(String mobile){
        Integer smsCode= RandomUtil.getNum(100000,999999);
        SMobileVerification verification=new SMobileVerification();

        verification.setMobile(mobile);
        verification.setVerification(smsCode);
        verification.setCreateTime(new Timestamp(DateUtils.getCurrentTime()));
        verification.setType(2);
        verification.setStatus(1);
        return mobileVerificationService.dynaInsert(verification);
    }



}
