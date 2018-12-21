package com.apiserver.data.core;

import java.util.List;
import java.util.Map;

/**
 *
 * @author jarvis
 * @date 2017/10/19
 */
public interface AbstractMapper<T> {


    int deleteByPrimaryKey(Long id);

    int insert(T record);

    int insertSelective(T record);

    T selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);

    List<T> selectAll();

    /**
     * 分页查询等
     * @param pagerParams
     * @return
     */
    List<T> selectByParams(Map<String, Object> pagerParams);

    int deletes(List<String> ids);

    int count(Map<String, Object> params);

}