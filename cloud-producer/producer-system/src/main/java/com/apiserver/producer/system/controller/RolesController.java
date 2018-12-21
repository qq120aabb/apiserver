package com.apiserver.producer.system.controller;

import com.apiserver.kernel.result.StatusCode;
import com.apiserver.kernel.utils.DateUtils;
import com.apiserver.kernel.utils.SnowFlake;
import com.apiserver.model.system.entity.SRoles;
import com.apiserver.model.system.entity.SUsers;
import com.apiserver.model.system.request.UserPageRequest;
import com.apiserver.producer.system.service.RolesService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

/**
 * @author jarvis
 * @date 2018-10-20
 */
@Api(description = "系统角色管理", tags = "RolesController", basePath = "/role")
@RestController
@RequestMapping(value = "/v1/role",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RolesController {

    Logger logger= LoggerFactory.getLogger(RolesController.class);

    @Autowired
    RolesService rolesService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token" , required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "【后台】根据角色ID，查询角色的详细信息", notes = "返回单个菜单的信息,菜单详细信息")
    @RequestMapping(value = "get",method = RequestMethod.GET)
    public SRoles get(@RequestParam(value = "id") Long id){
        return rolesService.dynaSelectByPrimaryKey(SRoles.builder().id(id).build());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token" , required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "【后台】保存和修改角色的信息", notes = "保存和修改角色的信息")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Map<String,Object> save(@RequestBody SRoles roles){
        Map<String,Object> result=new HashMap<>();

        SRoles rolesEntity=rolesService.dynaSelectByPrimaryKey(roles);

        if(rolesEntity==null){
            Map<String,Object> paramap=new HashMap<>();
            paramap.put("name",roles.getName());
            SRoles sRoles = rolesService.fetchOneByParameters(paramap);
            if(null==sRoles){
                roles.setCreateTime(new Timestamp(DateUtils.getCurrentTime()));
                roles.setModifyTime(new Timestamp(DateUtils.getCurrentTime()));
                rolesService.dynaInsert(roles);
            }else {
                result.put("statusCode", StatusCode.RoleNameExists.getCode().toString());
                result.put("statusMessage", StatusCode.RoleNameExists.getMeaning());
                return result;
            }

        }else {
            //更新操作 如果角色名字不一样 数据库存在则不能修改
            if(!rolesEntity.getName().equals(roles.getName())){
                Map<String,Object> paramap=new HashMap<>();
                paramap.put("name",roles.getName());
                SRoles sRoles = rolesService.fetchOneByParameters(paramap);
                if(null !=sRoles){
                    result.put("statusCode", StatusCode.RoleNameExists.getCode().toString());
                    result.put("statusMessage", StatusCode.RoleNameExists.getMeaning());
                    return result;
                }
            }
            roles.setModifyTime(new Timestamp(DateUtils.getCurrentTime()));
             rolesService.dynaUpdateByPrimaryKey(roles);
        }
        return result;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token" , required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "【后台】删除角色的信息", notes = "根据角色的ID,删除数据")
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public Integer delete(@RequestBody Long id){
        return rolesService.dynaDeleteByPrimaryKey(SRoles.builder().id(id).build());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token" , required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "【后台】用户角色列表", notes = "查询所有的用户角色")
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public List<SRoles> list(){

        return rolesService.fetchByParameters(null,null);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "【后台】根据查询的参数，查询用户的列表信息", notes = "返回用户的列表信息")
    @RequestMapping(value = "getAll", method = RequestMethod.GET)
    public PageInfo<SRoles> getAll(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                   @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                   @RequestParam(value = "roleName",required = false) String roleName) {

        PageHelper.startPage(pageNum, pageSize);
        List<String> likeParam = new ArrayList<>();
        likeParam.add("name");

        LinkedHashMap<String,Object> orderMap=new LinkedHashMap<>();
        orderMap.put("createTime","desc");
        List<SRoles> sRolesList = rolesService.dynaSelectAllLike(SRoles.builder().name(roleName)
                .build(), likeParam,orderMap);

        PageInfo<SRoles> pageInfo = new PageInfo<>(sRolesList);
        return pageInfo;
    }



}
