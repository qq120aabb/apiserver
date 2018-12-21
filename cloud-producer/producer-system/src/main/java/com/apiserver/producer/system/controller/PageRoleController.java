package com.apiserver.producer.system.controller;


import com.apiserver.kernel.result.StatusCode;
import com.apiserver.kernel.utils.Constant;
import com.apiserver.kernel.utils.DateUtils;
import com.apiserver.model.system.entity.SDictionary;
import com.apiserver.model.system.entity.SPageRole;
import com.apiserver.model.system.entity.SRoles;
import com.apiserver.model.system.response.Buttons;
import com.apiserver.model.system.response.PageRoleButtons;
import com.apiserver.producer.system.service.DictionaryService;
import com.apiserver.producer.system.service.PageRoleService;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author jarvis
 * @date 2018-10-20
 */
@Api(description = "系统角色的按钮权限", tags = "PageRoleController", basePath = "/pageRole")
@RestController
@RequestMapping(value = "/v1/pageRole", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PageRoleController {

    @Autowired
    PageRoleService pageRoleService;
    @Autowired
    DictionaryService dictionaryService;

    Logger logger = LoggerFactory.getLogger(PageRoleController.class);


    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "【后台】根据菜单和角色查询按钮的列表信息", notes = "返回菜单角色下的所有按钮列表信息")
    @RequestMapping(value = "getButtons", method = RequestMethod.GET)
    public List<Buttons> getButtons(@RequestParam(value = "menuId") Long menuId, @RequestParam(value = "roleId") Long roleId) {
        List<Buttons> list = new ArrayList<>();
        Map<String, Object> dictParamMap = new HashMap<>();
        dictParamMap.put("status", 1);
        dictParamMap.put("parentId", Constant.BUTTON_DICT);
        List<SDictionary> sDictionaries = dictionaryService.fetchByParameters(dictParamMap, null);

        Map<String, Object> pageRoleParamMap = new HashMap<>();
        pageRoleParamMap.put("menuId", menuId);
        pageRoleParamMap.put("roleId", roleId);
        List<SPageRole> sPageRoles = pageRoleService.fetchByParameters(pageRoleParamMap, null);

        if (!CollectionUtils.isEmpty(sDictionaries)) {
            for (SDictionary sDictionary : sDictionaries) {
                Buttons buttons = new Buttons();
                buttons.setDictId(String.valueOf(sDictionary.getId()));
                buttons.setDictName(sDictionary.getName());
                buttons.setDictValue(sDictionary.getValue());
                buttons.setIsChecked(0); //未选中
                if (!CollectionUtils.isEmpty(sPageRoles)) {
                    for (SPageRole sPageRole : sPageRoles) {
                        if (sPageRole.getDictionaryId().longValue() == sDictionary.getId().longValue()) {
                            buttons.setIsChecked(1); //选中
                        }
                    }
                }
                list.add(buttons);
            }
        }
        return list;
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "【后台】根据菜单和角色保存勾选按钮的列表", notes = "保存菜单角色下的所有勾选按钮列表")
    @RequestMapping(value = "saveButtons", method = RequestMethod.POST)
    public Integer saveButtons(@RequestBody PageRoleButtons pageRoleButtons) {

        Long menuId = Long.parseLong(pageRoleButtons.getMenuId());
        Long roleId = Long.parseLong(pageRoleButtons.getRoleId());
        int count=0;
        //先删除所有再添加
        pageRoleService.dynaDelete(SPageRole.builder().menuId(menuId)
                .roleId(roleId).build());

        List<Long> buttonsList = pageRoleButtons.getButtonsList();
        if (!CollectionUtils.isEmpty(buttonsList)) {
            for (Long buttonId : buttonsList) {
                SPageRole sPageRole = new SPageRole();
                sPageRole.setMenuId(menuId);
                sPageRole.setRoleId(roleId);
                sPageRole.setCreateId(pageRoleButtons.getCreateId());
                sPageRole.setCreateTime(new Timestamp(DateUtils.getCurrentTime()));
                sPageRole.setModifyTime(new Timestamp(DateUtils.getCurrentTime()));

                sPageRole.setDictionaryId(buttonId);
                count+= pageRoleService.dynaInsert(sPageRole);
            }
        }
        return count;
    }
}
