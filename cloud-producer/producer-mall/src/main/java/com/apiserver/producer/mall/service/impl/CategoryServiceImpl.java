package com.apiserver.producer.mall.service.impl;

import com.apiserver.data.service.BaseService;
import com.apiserver.model.mall.entity.MCategory;
import com.apiserver.producer.mall.mapper.CategoryMapper;
import com.apiserver.producer.mall.service.CategoryService;
import org.springframework.stereotype.Repository;
/**
 * @author jarvis
 * @date 2018-12-17
 */

@Repository
public class CategoryServiceImpl extends BaseService<CategoryMapper, MCategory> implements CategoryService {


}
