package com.apiserver.consumer.console.api.mall;

import com.apiserver.model.mall.entity.MCategory;
import com.apiserver.model.system.response.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "producer-mall/v1/category")
public interface CategoryInterface {

    /**
     * 根据id查询分类
     * @param id
     * @return
     */
    @RequestMapping(value = "get", method = RequestMethod.GET)
    MCategory get(@RequestParam(value = "id") Long id);

    /**
     * 根据父级Id查询分类
     * @param pageNum
     * @param pageSize
     * @param parentId
     * @return
     */
    @RequestMapping(value = "getByParentId", method = RequestMethod.GET)
    PageInfo<MCategory> getByParentId(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                      @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                      @RequestParam(value = "parentId") Long parentId);
}
