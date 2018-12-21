package com.apiserver.consumer.console.controller.mall;


import com.apiserver.consumer.console.api.mall.CategoryInterface;
import com.apiserver.kernel.result.JsonResult;
import com.apiserver.model.mall.entity.MCategory;
import com.apiserver.model.system.response.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author jarvis
 * @date 2018-12-17
 */
@Api(description = "商品分类管理", tags = "CategoryController", basePath = "/category")
@RestController
@RequestMapping(value = "/v1/category", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CategoryController {

    @Resource
    CategoryInterface categoryInterface;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "【后台】根据分类ID，查询分类的基本信息", notes = "返回单个商品分类的基本信息")
    @RequestMapping(value = "get", method = RequestMethod.GET)
    public JsonResult<MCategory> get(@RequestParam(value = "id") Long id) {
        return JsonResult.getSuccessResult(categoryInterface.get(id));
    }



    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "【后台】根据商品分类的parentId，查询商品分类列表信息", notes = "返回商品分类的列表信息")
    @RequestMapping(value = "getByParentId", method = RequestMethod.GET)
    public JsonResult<PageInfo<MCategory>> getByParentId(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                         @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                                         @RequestParam(value = "parentId") Long parentId) {
        PageInfo<MCategory> pageResult=  categoryInterface.getByParentId(pageNum,pageSize,parentId);
        return JsonResult.getSuccessResult(pageResult);
    }


}
