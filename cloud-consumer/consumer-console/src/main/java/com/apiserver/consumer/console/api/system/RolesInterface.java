package com.apiserver.consumer.console.api.system;


import com.apiserver.model.system.entity.SRoles;
import com.apiserver.model.system.response.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author jarvis
 * @date 2018-11-12
 */
@FeignClient(name = "producer-system/v1/role")
public interface RolesInterface {


    /**
     * 根据id 查询信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "get", method = RequestMethod.GET)
    SRoles get(@RequestParam(value = "id") Long id);


    /**
     * 保存角色信息
     *
     * @param roles
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    Map<String,Object> save(@RequestBody SRoles roles);

    /**
     * 删除角色的信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    Integer delete(@RequestBody Long id);


    /**
     * 查询所有的角色列表
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    List<SRoles> list();


    /**
     * 获取角色列表分页显示
     *
     * @param pageNum
     * @param pageSize
     * @param roleName
     * @return
     */
    @RequestMapping(value = "getAll", method = RequestMethod.GET)
    PageInfo<SRoles> getAll(@RequestParam(value = "pageNum") int pageNum,
                            @RequestParam(value = "pageSize") int pageSize,
                            @RequestParam(value = "roleName") String roleName);


}