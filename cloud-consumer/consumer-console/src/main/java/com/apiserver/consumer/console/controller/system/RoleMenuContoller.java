package com.apiserver.consumer.console.controller.system;

import com.apiserver.consumer.console.api.system.RoleMenuInterface;
import com.apiserver.kernel.result.JsonResult;
import com.apiserver.kernel.utils.TokenGenerator;
import com.apiserver.model.system.request.RoleMenuRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Api(description = "系统角色的菜单配置", tags = "RoleMenuController", basePath = "/roleMenu")
@RestController
@RequestMapping(value = "/v1/roleMenu", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RoleMenuContoller {
    @Resource
    RoleMenuInterface roleMenuInterface;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "保存角色ID设置菜单数据", notes = "保存角色ID设置菜单数据")
    @RequestMapping(value = "saveRolesMenu", method = RequestMethod.POST)
    public JsonResult<Integer> saveRolesMenu(@RequestBody @Valid RoleMenuRequest roleMenuRequest, ServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("Authorization");
        if (null != authorization) {
            Long userId = TokenGenerator.getUserId(authorization);
            roleMenuRequest.setCreateId(userId);
        }
        Integer count = roleMenuInterface.saveRolesMenu(roleMenuRequest);
        if (count == 0) {
            return JsonResult.getFailureResult();
        }
        return JsonResult.getSuccessResult();
    }
}
