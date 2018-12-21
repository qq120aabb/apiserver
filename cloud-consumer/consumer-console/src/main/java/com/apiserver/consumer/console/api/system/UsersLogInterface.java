package com.apiserver.consumer.console.api.system;

import com.apiserver.model.system.entity.SUserLog;
import com.apiserver.model.system.response.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "producer-system/v1/userLog")
public interface UsersLogInterface {
    /**
     * 获取用户日志列表分页显示
     *
     * @param pageNum
     * @param pageSize
     * @param account
     * @param userIp
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "getAll", method = RequestMethod.GET)
     PageInfo<SUserLog> getAll(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize",defaultValue="10") int pageSize,
                                         @RequestParam(value = "account", required = false) String  account,
                                         @RequestParam(value = "userIp", required = false) String userIp,
                                         @RequestParam(value = "startTime", required = false) String startTime,
                                         @RequestParam(value = "endTime", required = false) String endTime,
                                         @RequestParam(value = "successFlag",required = false)Integer successFlag);
}
