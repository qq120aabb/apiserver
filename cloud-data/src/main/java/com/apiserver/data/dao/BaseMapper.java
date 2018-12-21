package com.apiserver.data.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public  interface BaseMapper<T> extends  SqlMapper<T> {

    T getOneByParameters(@Param("queryMap") Map<String, Object> queryMap);

    List<T> getByParameters(@Param("queryMap") Map<String, Object> queryMap, @Param("orderMap") Map<String, Object> orderMap);

    int fetchByAllOrParameters(@Param("mapperQueryMap")Map<String, Object> mapperQueryMap);
}
