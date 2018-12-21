package com.apiserver.producer.system.mapper;

import com.apiserver.data.dao.BaseMapper;
import com.apiserver.model.system.entity.SDictionary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author jarvis
 */
public interface DictionaryMapper extends BaseMapper<SDictionary> {

    List<SDictionary> queryDictionaryList(@Param("parentId") Long parentId);

    List<SDictionary> getBySDictionary(SDictionary sDictionary);
}