package com.apiserver.consumer.console.controller.system;

import com.apiserver.consumer.console.api.system.DictionaryInterface;
import com.apiserver.consumer.console.api.system.MenuInterface;
import com.apiserver.kernel.result.JsonResult;
import com.apiserver.kernel.result.StatusCode;
import com.apiserver.kernel.utils.DateUtils;
import com.apiserver.model.system.entity.SDictionary;
import com.apiserver.model.system.entity.SRoles;
import com.apiserver.model.system.response.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.MapUtils;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "系统数据字典管理", tags = "DictionaryController", basePath = "/dictionary")
@RestController
@RequestMapping(value = "/v1/dictionary", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DictionaryController {

    @Resource
    DictionaryInterface dictionaryInterface;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "根据字典ID查询字典基本信息", notes = "返回单个字典基本信息")
    @RequestMapping(value = "get", method = RequestMethod.GET)
    public JsonResult<SDictionary> get(@RequestParam(value = "id") Long id) {
        return JsonResult.getSuccessResult(dictionaryInterface.get(id));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "保存和修改字典基本信息", notes = "保存和修改字典基本信息")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public JsonResult<HashMap<String, Object>> save(@RequestBody SDictionary dictionary) {
        HashMap<String, Object> result = dictionaryInterface.save(dictionary);

        if (result.get("statusCode").toString().equals("201")) {
            return JsonResult.getSuccessResult(result);
        } else {
            return JsonResult.getFailureResult(StatusCode.INVALID_REQUEST,result);
        }
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "删除字典的信息", notes = "根据字典的ID,删除数据")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public  JsonResult<Map<String, Object>> delete(@RequestBody Long id) {
        Map<String, Object> delete = dictionaryInterface.delete(id);
        if(MapUtils.isNotEmpty(delete)){
            return JsonResult.getFailureResult(StatusCode.INVALID_REQUEST, delete);
        }else {
            return JsonResult.getSuccessResult();
        }
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "字典信息列表", notes = "查询所有的字典信息")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public  JsonResult<List<SDictionary>>  list(@RequestParam(value = "parentId", required = false) Long parentId) {

        return JsonResult.getSuccessResult(dictionaryInterface.list(parentId));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "根据parentId查询的参数，查询数据字典子数据", notes = "返回数据字典子数据列表信息")
    @RequestMapping(value = "getByParentId", method = RequestMethod.GET)
    public JsonResult<PageInfo<SDictionary>> getByParentId(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                               @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                               @RequestParam(value = "parentId") Long parentId) {
        PageInfo<SDictionary> pageResult=  dictionaryInterface.getByParentId(pageNum,pageSize,parentId);
        return JsonResult.getSuccessResult(pageResult);
    }
}
