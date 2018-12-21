package com.apiserver.producer.system.service;

import com.apiserver.data.service.SqlService;
import com.apiserver.model.system.entity.SDictionary;

import java.util.List;


/**
 * @author jarvis
 * @date 2018-10-25
 */
public interface DictionaryService extends SqlService<SDictionary> {
    List<SDictionary> queryDictionaryList(Long parentId);
    List<SDictionary> getBySDictionary(SDictionary sDictionary);
}
