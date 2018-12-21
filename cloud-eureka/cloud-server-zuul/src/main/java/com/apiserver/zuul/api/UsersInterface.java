package com.apiserver.zuul.api;

import com.apiserver.model.system.entity.SUsers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author jarvis
 */
@FeignClient(name = "producer-system/v1/users")
public interface UsersInterface {

    /**
     * 根据id 获取用户信息，feign调用
     * @param id
     * @return
     */
    @RequestMapping(value = "get",method = RequestMethod.GET)
    SUsers get(@RequestParam(value = "id") Long id);

}
