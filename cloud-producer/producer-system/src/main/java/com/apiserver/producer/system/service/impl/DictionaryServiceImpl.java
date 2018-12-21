package com.apiserver.producer.system.service.impl;

import com.apiserver.data.service.BaseService;
import com.apiserver.model.system.entity.SDictionary;
import com.apiserver.producer.system.mapper.DictionaryMapper;
import com.apiserver.producer.system.service.DictionaryService;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jarvis
 */
@Repository
public class DictionaryServiceImpl  extends BaseService<DictionaryMapper, SDictionary> implements DictionaryService {


    @Override
    public List<SDictionary> queryDictionaryList(Long parentId) {
        return dao.queryDictionaryList(parentId);
    }

    @Override
    public List<SDictionary> getBySDictionary(SDictionary sDictionary){
        return dao.getBySDictionary(sDictionary);
    }

}
