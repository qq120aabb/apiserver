package com.apiserver.consumer.console.controller.system;

import com.apiserver.consumer.console.api.system.PageRoleInterface;
import com.apiserver.consumer.console.api.system.RolesInterface;
import com.apiserver.kernel.result.JsonResult;
import com.apiserver.kernel.utils.TokenGenerator;
import com.apiserver.model.system.response.Buttons;
import com.apiserver.model.system.response.PageRoleButtons;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Api(description = "系统角色的按钮权限", tags = "PageRoleController", basePath = "/pageRole")
@RestController
@RequestMapping(value = "/v1/pageRole", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PageRoleController {

    @Resource
    PageRoleInterface pageRoleInterface;


    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "【后台】根据菜单和角色查询按钮的列表信息", notes = "返回菜单角色下的所有按钮列表信息")
    @RequestMapping(value = "getButtons", method = RequestMethod.GET)
    public JsonResult<List<Buttons>> getButtons(@RequestParam(value = "menuId") Long menuId, @RequestParam(value = "roleId") Long roleId) {
        return JsonResult.getSuccessResult(pageRoleInterface.getButtons(menuId, roleId));
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "【后台】根据菜单和角色保存勾选按钮的列表", notes = "保存菜单角色下的所有勾选按钮列表")
    @RequestMapping(value = "saveButtons", method = RequestMethod.POST)
    public JsonResult<Integer> saveButtons(@RequestBody @Valid PageRoleButtons pageRoleButtons, ServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("Authorization");
        if (null != authorization) {
            Long userId = TokenGenerator.getUserId(authorization);
            pageRoleButtons.setCreateId(userId);
            pageRoleButtons.setModifyId(userId);
        }
        Integer count = pageRoleInterface.saveButtons(pageRoleButtons);
        if (count == 0) {
            return JsonResult.getFailureResult();
        }
        return JsonResult.getSuccessResult();
    }
}
