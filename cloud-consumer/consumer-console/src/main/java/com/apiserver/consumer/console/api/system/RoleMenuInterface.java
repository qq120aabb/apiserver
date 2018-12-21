package com.apiserver.consumer.console.api.system;

import com.apiserver.model.system.request.RoleMenuRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "producer-system/v1/roleMenu")
public interface RoleMenuInterface {
    @ApiOperation(value = "保存角色ID设置菜单数据", notes = "保存角色ID设置菜单数据")
    @RequestMapping(value = "saveRolesMenu", method = RequestMethod.POST)
    Integer saveRolesMenu(@RequestBody RoleMenuRequest roleMenuRequest);
}
