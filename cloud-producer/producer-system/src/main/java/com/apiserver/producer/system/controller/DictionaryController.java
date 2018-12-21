package com.apiserver.producer.system.controller;

import com.apiserver.kernel.result.StatusCode;
import com.apiserver.kernel.utils.DateUtils;
import com.apiserver.model.system.entity.SDictionary;
import com.apiserver.producer.system.service.DictionaryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author jarvis
 * @date 2018-10-25
 */
@Api(description = "系统数据字典管理", tags = "DictionaryController", basePath = "/dictionary")
@RestController
@RequestMapping(value = "/v1/dictionary", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DictionaryController {

    @Autowired
    DictionaryService dictionaryService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "根据字典ID查询字典基本信息", notes = "返回单个字典基本信息")
    @RequestMapping(value = "get", method = RequestMethod.GET)
    public SDictionary get(@RequestParam(value = "id") Long id) {
        return dictionaryService.dynaSelectByPrimaryKey(SDictionary.builder().id(id).build());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "保存和修改字典基本信息", notes = "保存和修改字典基本信息")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public HashMap<String, Object> save(@RequestBody SDictionary dictionary) {
        HashMap<String, Object> hashMap = new HashMap<>();
        List<SDictionary> doubleName = dictionaryService.getBySDictionary(dictionary);
        SDictionary sDictionary = dictionaryService.dynaSelectByPrimaryKey(dictionary);
        if (null != sDictionary) {

            SDictionary presence=doubleName.stream().filter(c->c.getId().longValue()!=sDictionary.getId()).filter(c->c.getValue().equals(dictionary.getValue())).findFirst().orElse(null);
            if(presence!=null){
                hashMap.put("statusCode", StatusCode.DictionaryValueAlreadyExists.getCode().toString());
                hashMap.put("statusMessage", StatusCode.DictionaryValueAlreadyExists.getMeaning());
                return hashMap;
            }
            sDictionary.setModifyTime(new Timestamp(DateUtils.getCurrentTime()));
            BeanUtils.copyProperties(dictionary, sDictionary);
            dictionaryService.dynaUpdateByPrimaryKey(sDictionary);
            hashMap.put("statusCode", StatusCode.CREATED.getCode().toString());
            hashMap.put("statusMessage", StatusCode.CREATED.getMeaning());
            return hashMap;
        } else {
            SDictionary presence=doubleName.stream().filter(s->s.getValue().equals(dictionary.getValue())).findFirst().orElse(null);
            if(presence!=null){
                hashMap.put("statusCode", StatusCode.DictionaryValueAlreadyExists.getCode().toString());
                hashMap.put("statusMessage", StatusCode.DictionaryValueAlreadyExists.getMeaning());
                return hashMap;
            }
            dictionary.setCreateTime(new Timestamp(DateUtils.getCurrentTime()));
            dictionary.setModifyTime(new Timestamp(DateUtils.getCurrentTime()));
            dictionaryService.dynaInsert(dictionary);
            hashMap.put("statusCode", StatusCode.CREATED.getCode().toString());
            hashMap.put("statusMessage", StatusCode.CREATED.getMeaning());
            return hashMap;
        }
    }



    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "删除字典的信息", notes = "根据字典的ID,删除数据")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Map<String, Object> delete(@RequestBody Long id) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("parentId", id);
        List<SDictionary> sDictionaries = dictionaryService.fetchByParameters(queryMap, null);
        if (!CollectionUtils.isEmpty(sDictionaries)) {
            resultMap.put("statusCode", StatusCode.ExistChildrenDict.getCode().toString());
            resultMap.put("statusMessage", StatusCode.ExistChildrenDict.getMeaning());
        } else {
            dictionaryService.dynaDelete(SDictionary.builder().id(id).build());
        }
        return resultMap;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "字典信息列表", notes = "查询所有的字典信息")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public List<SDictionary> list(@RequestParam(value = "parentId", required = false) Long parentId) {

        return dictionaryService.queryDictionaryList(parentId);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "【后台】根据查询的参数，查询数据字典列表信息", notes = "返回数据字典列表信息")
    @RequestMapping(value = "getByParentId", method = RequestMethod.GET)
    public PageInfo<SDictionary> getByParentId(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                   @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                   @RequestParam(value = "parentId") Long parentId) {
        PageHelper.startPage(pageNum, pageSize);
        List<String> likeParam = new ArrayList<>();
        likeParam.add("parentId");
        LinkedHashMap<String,Object> orderMap=new LinkedHashMap<>();
        orderMap.put("createTime","desc");
        List<SDictionary> sDictionaryList = dictionaryService.dynaSelectAllLike(SDictionary.builder().parentId(parentId)
                .build(),likeParam,orderMap);
        PageInfo<SDictionary> pageInfo = new PageInfo<>(sDictionaryList);
        return pageInfo;
    }
}
