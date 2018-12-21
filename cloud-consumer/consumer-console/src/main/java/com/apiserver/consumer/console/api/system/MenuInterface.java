package com.apiserver.consumer.console.api.system;

import com.apiserver.model.system.entity.SMenu;
import com.apiserver.model.system.response.MenuJson;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author jarvis
 * @date 2018-11-12
 */
@FeignClient(name = "producer-system/v1/menu")
public interface MenuInterface {

    /**
     * 根据id产线
     * @param id
     * @return
     */
    @RequestMapping(value = "get",method = RequestMethod.GET)
    SMenu get(@RequestParam(value = "id") Long id);


    /**
     * 根据parentId 查询菜单的信息
     * @param parentId
     * @return
     */
    @RequestMapping(value = "getByParentId",method = RequestMethod.GET)
    List<SMenu> getByParentId(@RequestParam(value = "parentId",required = false) Long parentId);


    @RequestMapping(value = "delete",method = RequestMethod.POST)
    Integer delete(@RequestBody Long id);

    /**
     * 根据提交的数据保存菜单信息
     * @param menuEntity
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    Integer save(@RequestBody SMenu menuEntity);

    @ApiOperation(value = "获取菜单json数据", notes = "返回菜单json数据")
    @RequestMapping(value = "getMenuJson", method = RequestMethod.GET)
     List<MenuJson> getMenuJson(@RequestParam(value = "userId")Long userId) ;

    @ApiOperation(value = "根据角色ID获取菜单json数据", notes = "返回角色ID菜单json数据")
    @RequestMapping(value = "getMenuJsonByRoleId", method = RequestMethod.GET)
    List<MenuJson> getMenuJsonByRoleId(@RequestParam(value = "roleId", required = false) Long roleId);

}
