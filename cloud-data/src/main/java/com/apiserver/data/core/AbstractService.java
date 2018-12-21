package com.apiserver.data.core;

import com.apiserver.data.page.Pager;

import java.util.List;
import java.util.Map;

/**
 * Created by jarvis on 2018/9/19.
 */
public interface AbstractService<T> {

    int deleteByPrimaryKey(Long id);

    int insert(T record);

    int insertSelective(T record);

    T selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);

    List<T> list();

    List<T> page(Map<String, Object> pagerParams, Pager pager);

    int deletes(List<String> ids);

    int count(Map<String, Object> pagerParams);

    /**
     * 分页查询等
     * @param pagerParams
     * @return
     */
    List<T> selectByParams(Map<String, Object> pagerParams);
}