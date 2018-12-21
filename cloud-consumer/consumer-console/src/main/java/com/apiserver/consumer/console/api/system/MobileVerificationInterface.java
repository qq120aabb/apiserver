package com.apiserver.consumer.console.api.system;

import com.apiserver.model.system.entity.SMobileVerification;
import com.apiserver.model.system.response.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

/**
 * @author jarvis
 * @date 2018-11-12
 */
@FeignClient(name = "producer-system/v1/mobileVerification")
public interface MobileVerificationInterface {

    /**
     * 根据id 查询单个数据
     * @param id
     * @return
     */
    @RequestMapping(value = "get",method = RequestMethod.GET)
    SMobileVerification get(@RequestParam(value = "id") Long id);

    /**
     * 后台查询的列表
     * @param pageNum
     * @param pageSize
     * @param mobile
     * @return
     */
    @RequestMapping(value = "getAll", method = RequestMethod.GET)
    PageInfo<SMobileVerification> getAll(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                         @RequestParam(value = "mobile",required = false) String mobile);

     /**
     * 发送验证码
     * @param mobile
     * @return
     */
    @RequestMapping(value = "sendVerificationCode", method = RequestMethod.GET)
     HashMap<String, Object> sendVerificationCode(@RequestParam(value = "mobile") String mobile);
}
