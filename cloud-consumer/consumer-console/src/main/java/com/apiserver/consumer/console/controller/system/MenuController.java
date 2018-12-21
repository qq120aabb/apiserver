package com.apiserver.consumer.console.controller.system;


import com.apiserver.consumer.console.api.system.MenuInterface;
import com.apiserver.kernel.result.JsonResult;
import com.apiserver.kernel.utils.TokenGenerator;
import com.apiserver.model.system.entity.SMenu;
import com.apiserver.model.system.response.MenuJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @author jarvis
 * @date 2018-10-20
 */
@Api(description = "系统菜单管理", tags = "MenuController", basePath = "/menu")
@RestController
@RequestMapping(value = "/v1/menu",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MenuController {

    @Resource
    MenuInterface menuInterface;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token" , required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "根据菜单ID，查询菜单的详细信息", notes = "返回单个菜单的信息,菜单详细信息")
    @RequestMapping(value = "get",method = RequestMethod.GET)
    public JsonResult<SMenu> get(@RequestParam(value = "id") Long id){
        return JsonResult.getSuccessResult(menuInterface.get(id));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token" , required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "根据菜单父级ID，查询该parentId的子菜单列表", notes = "返回parentId的子菜单列表信息")
    @RequestMapping(value = "getByParentId",method = RequestMethod.GET)
    public JsonResult<List<SMenu>> getByParentId(@RequestParam(value = "parentId",required = false) Long parentId){

        return JsonResult.getSuccessResult(menuInterface.getByParentId(parentId));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token" , required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "保存菜单的信息", notes = "保存菜单的信息")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public JsonResult<Integer> save(@RequestBody @Valid SMenu menuEntity, ServletRequest request){
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("Authorization");
        if (null != authorization) {
            Long userId = TokenGenerator.getUserId(authorization);
            menuEntity.setCreateId(userId);
            if (null != menuEntity.getId()) {
                menuEntity.setModifyId(userId);
            }
        }
        return  JsonResult.getSuccessResult(menuInterface.save(menuEntity));
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token" , required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "删除菜单的信息", notes = "根据菜单的ID,删除数据")
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public JsonResult<Integer> delete(@RequestBody Long id){
        Integer count = menuInterface.delete(id);
        if(count ==0){
            return  JsonResult.getFailureResult();
        }
        return JsonResult.getSuccessResult();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "获取用户菜单json数据", notes = "返回用户菜单数据")
    @RequestMapping(value = "getMenuJson", method = RequestMethod.GET)
    public JsonResult<List<MenuJson>>  getMenuJson(ServletRequest request) {
        Long userId=null;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("Authorization");
        if (null != authorization) {
             userId = TokenGenerator.getUserId(authorization);

        }
        if(null==userId){
            return JsonResult.getFailureResult();
        }

        return JsonResult.getSuccessResult(menuInterface.getMenuJson(userId));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "根据角色ID获取菜单json树数据", notes = "返回角色ID菜单json数据,角色ID为空 返回完整菜单树")
    @RequestMapping(value = "getMenuJsonByRoleId", method = RequestMethod.GET)
    public JsonResult<List<MenuJson>> getMenuJsonByRoleId(@RequestParam(value = "roleId", required = false) Long roleId){
        return JsonResult.getSuccessResult(menuInterface.getMenuJsonByRoleId(roleId));
    }
}
