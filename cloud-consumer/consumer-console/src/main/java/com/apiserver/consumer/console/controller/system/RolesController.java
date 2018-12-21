package com.apiserver.consumer.console.controller.system;

import com.apiserver.consumer.console.api.system.RolesInterface;
import com.apiserver.kernel.result.JsonResult;
import com.apiserver.kernel.utils.TokenGenerator;
import com.apiserver.model.system.entity.SRoles;
import com.apiserver.model.system.response.PageInfo;
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
import java.util.Map;


/**
 * @author jarvis
 * @date 2018-10-20
 */
@Api(description = "系统角色管理", tags = "RolesController", basePath = "/role")
@RestController
@RequestMapping(value = "/v1/role", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RolesController {

    @Resource
    RolesInterface rolesInterface;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "根据角色ID，查询角色的详细信息", notes = "返回单个菜单的信息,菜单详细信息")
    @RequestMapping(value = "get", method = RequestMethod.GET)
    public JsonResult<SRoles> get(@RequestParam(value = "id") Long id) {
        return JsonResult.getSuccessResult(rolesInterface.get(id));
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "保存和修改角色的信息", notes = "保存和修改角色的信息")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public JsonResult<Map<String, Object>> save(@RequestBody @Valid SRoles roles, ServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("Authorization");
        if (null != authorization) {
            Long userId = TokenGenerator.getUserId(authorization);
            roles.setCreateId(userId);
            if (null != roles.getId()) {
                roles.setModifyId(userId);
            }
        }
        return JsonResult.getSuccessResult(rolesInterface.save(roles));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "删除角色的信息", notes = "根据角色的ID,删除数据")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public JsonResult<Integer> delete(@RequestBody Long id) {
        Integer count = rolesInterface.delete(id);
        if (count == 0) {
            return JsonResult.getFailureResult();
        }
        return JsonResult.getSuccessResult();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "用户角色列表", notes = "查询所有的用户角色")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public JsonResult<List<SRoles>> list() {
        List<SRoles> list = rolesInterface.list();
        return JsonResult.getSuccessResult(list);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "根据查询的参数，查询用户的列表信息", notes = "返回用户的列表信息")
    @RequestMapping(value = "getAll", method = RequestMethod.GET)
    public JsonResult<PageInfo<SRoles>> getAll(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                               @RequestParam(value = "roleName", required = false) String roleName) {
        PageInfo<SRoles> pageResult = rolesInterface.getAll(pageNum, pageSize, roleName);

        return JsonResult.getSuccessResult(pageResult);
    }
}
