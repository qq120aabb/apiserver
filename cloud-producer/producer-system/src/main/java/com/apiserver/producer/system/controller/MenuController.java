package com.apiserver.producer.system.controller;


import com.apiserver.kernel.utils.DateUtils;
import com.apiserver.model.system.entity.SMenu;
import com.apiserver.model.system.response.MenuJson;
import com.apiserver.producer.system.service.MenuService;
import com.apiserver.producer.system.service.RoleMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jarvis
 * @date 2018-10-20
 */
@Api(description = "系统菜单管理", tags = "MenuController", basePath = "/menu")
@RestController
@RequestMapping(value = "/v1/menu", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MenuController {

    @Autowired
    MenuService menuService;

    @Autowired
    RoleMenuService roleMenuService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "根据菜单ID，查询菜单的详细信息", notes = "返回单个菜单的信息,菜单详细信息")
    @RequestMapping(value = "get", method = RequestMethod.GET)
    public SMenu get(@RequestParam(value = "id") Long id) {
        return menuService.dynaSelectByPrimaryKey(SMenu.builder().id(id).build());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "根据菜单父级ID，查询该parentId的子菜单列表", notes = "返回parentId的子菜单列表信息")
    @RequestMapping(value = "getByParentId", method = RequestMethod.GET)
    public List<SMenu> getByParentId(@RequestParam(value = "parentId", required = false) Long parentId) {
        Map<String, Object> querMap = new HashMap<>();
        querMap.put("parentId", parentId == null ? 0l : parentId);
        return menuService.fetchByParameters(querMap, null);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "保存菜单的信息", notes = "保存菜单的信息")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Integer save(@RequestBody SMenu menuEntity) {

        SMenu sMenuEntity = menuService.dynaSelectByPrimaryKey(menuEntity);
        if (sMenuEntity == null) {
            menuEntity.setCreateTime(new Timestamp(DateUtils.getCurrentTime()));
            menuEntity.setModifyTime(new Timestamp(DateUtils.getCurrentTime()));
            return menuService.dynaInsert(menuEntity);
        } else {
            menuEntity.setModifyTime(new Timestamp(DateUtils.getCurrentTime()));
            return menuService.dynaUpdateByPrimaryKey(menuEntity);
        }
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "删除菜单的信息", notes = "根据菜单的ID,删除数据")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Integer delete(@RequestBody Long id) {
        return menuService.dynaDelete(SMenu.builder().id(id).build());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "获取用户菜单json数据", notes = "返回用户菜单json数据")
    @RequestMapping(value = "getMenuJson", method = RequestMethod.GET)
    public List<MenuJson> getMenuJson(@RequestParam(value = "userId") Long userId) {
        List<MenuJson> resultList = new ArrayList<>();
        List<SMenu> sMenus = menuService.queryMenuByUserId(userId);

        if (!CollectionUtils.isEmpty(sMenus)) {
            for (SMenu sMenu : sMenus) {
                if (sMenu.getParentId().longValue() == 0) {
                    MenuJson menuJson1 = new MenuJson();
                    menuJson1.setId(sMenu.getId());
                    menuJson1.setLabel(sMenu.getName());
                    menuJson1.setParentId(sMenu.getParentId());
                    menuJson1.setSort(sMenu.getSortId());
                    menuJson1.setUrl(sMenu.getUrl());
                    menuJson1.setIconClass(sMenu.getIconClass());
                    resultList.add(menuJson1);
                } else {
                    continue;
                }
            }
            if (!CollectionUtils.isEmpty(resultList)) {
                for (MenuJson menuJson : resultList) {
                    Long id = menuJson.getId();
                    List<MenuJson> menuJsons = creatMenuJsonChild(id, sMenus);
                    menuJson.setChildren(menuJsons);
                }
            }
        }

        return resultList;
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "根据角色ID获取菜单json树数据", notes = "返回角色ID菜单json数据,角色ID为空 返回完整菜单树")
    @RequestMapping(value = "getMenuJsonByRoleId", method = RequestMethod.GET)
    public List<MenuJson> getMenuJsonByRoleId(@RequestParam(value = "roleId", required = false) Long roleId) {
        List<MenuJson> resultList = new ArrayList<>();
        Map<String, Object> paramMap = new HashMap<>();
        Map<String, Object> orderMap = new HashMap<>();
        orderMap.put("sortId", "asc");
        List<SMenu> sMenus = menuService.fetchByParameters(paramMap, orderMap);
        if (!CollectionUtils.isEmpty(sMenus)) {
            if (null != roleId) {
                List<SMenu> sRolesMenus = menuService.queryMenuByRoleId(roleId);
                if (!CollectionUtils.isEmpty(sRolesMenus)) {
                    for (SMenu sMenu : sMenus) {
                        for (SMenu roleMenu : sRolesMenus) {
                            if (sMenu.getId().longValue() == roleMenu.getId().longValue()) {
                                sMenu.setChecked(1);
                            }
                        }
                    }
                }
            }


            for (SMenu sMenu : sMenus) {
                if (sMenu.getParentId().longValue() == 0) {
                    MenuJson menuJson1 = new MenuJson();
                    menuJson1.setId(sMenu.getId());
                    menuJson1.setLabel(sMenu.getName());
                    menuJson1.setParentId(sMenu.getParentId());
                    menuJson1.setChecked(sMenu.getChecked() == null ? 0 : sMenu.getChecked());
                    menuJson1.setSort(sMenu.getSortId());
                    menuJson1.setUrl(sMenu.getUrl());
                    menuJson1.setIconClass(sMenu.getIconClass());
                    resultList.add(menuJson1);
                } else {
                    continue;
                }
            }
            if (!CollectionUtils.isEmpty(resultList)) {
                for (MenuJson menuJson : resultList) {
                    Long id = menuJson.getId();
                    List<MenuJson> menuJsons = creatMenuJsonChild(id, sMenus);
                    menuJson.setChildren(menuJsons);
                }
            }
        }

        return resultList;
    }


    public List<MenuJson> creatMenuJsonChild(Long id, List<SMenu> sMenus) {
        List<MenuJson> menuJsons = new ArrayList<>();

        for (SMenu sMenu : sMenus) { //菜单遍历
            Long parentIdInner = sMenu.getParentId();
            Long cid = sMenu.getId();
            if (id.longValue() == parentIdInner.longValue()) {
                MenuJson menuJson = new MenuJson();
                menuJson.setId(sMenu.getId());
                menuJson.setLabel(sMenu.getName());
                menuJson.setParentId(sMenu.getParentId());
                menuJson.setSort(sMenu.getSortId());
                menuJson.setChecked(sMenu.getChecked() == null ? 0 : sMenu.getChecked());
                menuJson.setUrl(sMenu.getUrl());
                menuJson.setIconClass(sMenu.getIconClass());
                menuJsons.add(menuJson);
                List<MenuJson> menuJsonInner = creatMenuJsonChild(cid, sMenus);

                menuJson.setChildren(menuJsonInner);
            }
        }
        return menuJsons;
    }



}
