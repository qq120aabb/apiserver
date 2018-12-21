package com.apiserver.consumer.console.api.system;


import com.apiserver.model.system.entity.SDictionary;
import com.apiserver.model.system.response.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@FeignClient(name = "producer-system/v1/dictionary")
public interface DictionaryInterface {

    @ApiOperation(value = "根据字典ID查询字典基本信息", notes = "返回单个字典基本信息")
    @RequestMapping(value = "get", method = RequestMethod.GET)
     SDictionary get(@RequestParam(value = "id") Long id);

    @ApiOperation(value = "保存和修改字典基本信息", notes = "保存和修改字典基本信息")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    HashMap<String, Object> save(@RequestBody SDictionary dictionary);


    @ApiOperation(value = "删除字典的信息", notes = "根据字典的ID,删除数据")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
     Map<String, Object> delete(@RequestBody Long id);


    @ApiOperation(value = "字典信息列表", notes = "查询所有的字典信息")
    @RequestMapping(value = "list", method = RequestMethod.GET)
     List<SDictionary> list(@RequestParam(value = "parentId", required = false) Long parentId);


    /**
     * 获取数据字典列表分页显示
     *
     * @param pageNum
     * @param pageSize
     * @param parentId
     * @return
     */
    @RequestMapping(value = "getByParentId", method = RequestMethod.GET)
    PageInfo<SDictionary> getByParentId(@RequestParam(value = "pageNum") int pageNum,
                            @RequestParam(value = "pageSize") int pageSize,
                            @RequestParam(value = "parentId") Long  parentId);
}
