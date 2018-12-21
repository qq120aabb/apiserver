package com.apiserver.producer.mall.controller;


import com.apiserver.model.mall.entity.MCategory;
import com.apiserver.producer.mall.service.CategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author jarvis
 * @date 2018-10-25
 */
@Api(description = "商品服务分类管理", tags = "CategoryController", basePath = "/category")
@RestController
@RequestMapping(value = "/v1/category",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CategoryController {

    @Autowired
    CategoryService categoryService;


    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "【后台】根据分类ID，查询分类的基本信息", notes = "返回单个商品分类的基本信息")
    @RequestMapping(value = "get", method = RequestMethod.GET)
    public MCategory get(@RequestParam(value = "id") Long id) {
        return categoryService.dynaSelectByPrimaryKey(MCategory.builder().id(id).build());
    }



    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "【后台】根据商品分类的parentId，查询商品分类列表信息", notes = "返回商品分类的列表信息")
    @RequestMapping(value = "getByParentId", method = RequestMethod.GET)
    public PageInfo<MCategory> getByParentId(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                             @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                             @RequestParam(value = "parentId") Long parentId) {
        PageHelper.startPage(pageNum, pageSize);
        List<String> likeParam = new ArrayList<>();
        likeParam.add("parentId");
        LinkedHashMap<String,Object> orderMap=new LinkedHashMap<>();
        orderMap.put("createTime","desc");
        List<MCategory> categoryList = categoryService.dynaSelectAllLike(MCategory.builder().parentId(parentId)
                .build(),likeParam,orderMap);
        PageInfo<MCategory> pageInfo = new PageInfo<>(categoryList);
        return pageInfo;
    }



}
