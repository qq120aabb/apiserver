package com.apiserver.producer.system.controller;

import com.apiserver.model.system.entity.SRoleMenu;
import com.apiserver.model.system.request.RoleMenuRequest;
import com.apiserver.producer.system.service.RoleMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jarvis
 * @date 2018-10-25
 */
@Api(description = "系统角色的菜单配置", tags = "RoleMenuController", basePath = "/roleMenu")
@RestController
@RequestMapping(value = "/v1/roleMenu",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RoleMenuController {

    @Autowired
    RoleMenuService roleMenuService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "保存角色ID设置菜单数据", notes = "保存角色ID设置菜单数据")
    @RequestMapping(value = "saveRolesMenu", method = RequestMethod.POST)
    public Integer saveRolesMenu(@RequestBody RoleMenuRequest roleMenuRequest) {
        int count=0;
        List<Long> menuIds = roleMenuRequest.getMenuIds();
        Long roleId = roleMenuRequest.getRoleId();
        roleMenuService.dynaDelete(SRoleMenu.builder().roleId(roleId).build());
        if (!CollectionUtils.isEmpty(menuIds)) {
            for (Long menuId : menuIds) {
                SRoleMenu roleMenu=new SRoleMenu();
                roleMenu.setCreateId(roleMenuRequest.getCreateId());
                roleMenu.setMenuId(menuId);
                roleMenu.setRoleId(roleId);
                count+= roleMenuService.dynaInsert(roleMenu);
            }
        }

        return count;

    }
}
