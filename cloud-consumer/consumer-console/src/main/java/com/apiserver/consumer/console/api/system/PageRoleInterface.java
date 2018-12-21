package com.apiserver.consumer.console.api.system;

import com.apiserver.model.system.response.Buttons;
import com.apiserver.model.system.response.PageRoleButtons;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "producer-system/v1/pageRole")
public interface PageRoleInterface {
    @ApiOperation(value = "【后台】根据菜单和角色查询按钮的列表信息", notes = "返回菜单角色下的所有按钮列表信息")
    @RequestMapping(value = "getButtons", method = RequestMethod.GET)
    public List<Buttons> getButtons(@RequestParam(value = "menuId") Long menuId, @RequestParam(value = "roleId") Long roleId) ;



    @ApiOperation(value = "【后台】根据菜单和角色保存勾选按钮的列表", notes = "保存菜单角色下的所有勾选按钮列表")
    @RequestMapping(value = "saveButtons", method = RequestMethod.POST)
    public Integer saveButtons(@RequestBody PageRoleButtons pageRoleButtons) ;
}
