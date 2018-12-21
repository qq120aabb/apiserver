package com.apiserver.data.core;

import com.apiserver.data.page.Pager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jarvis
 * @date 2017/10/25
 */
public abstract class AbstractServiceImpl<T> implements AbstractService<T> {

    protected abstract AbstractMapper<T> getAbstractMapper();

    @Override
    public int deleteByPrimaryKey(Long id) {
        return getAbstractMapper().deleteByPrimaryKey(id);
    }

    @Override
    public int insert(T record) {
        return getAbstractMapper().insert(record);
    }

    @Override
    public int insertSelective(T record) {
        return getAbstractMapper().insertSelective(record);
    }

    @Override
    public T selectByPrimaryKey(Long id) {
        return getAbstractMapper().selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(T record) {
        return getAbstractMapper().updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(T record) {
        return getAbstractMapper().updateByPrimaryKey(record);
    }

    @Override
    public List<T> list() {
        return getAbstractMapper().selectAll();
    }

    @Override
    public List<T> page(Map<String, Object> params, Pager pager) {

        if(params == null){
            params = new HashMap<String, Object>();
        }
        if(pager != null){
            params.put("end",pager.getPageSize()*(pager.getPageIndex()+1));
            params.put("start",(pager.getPageIndex())*pager.getPageSize());
        }
        Object orderBy = params.get("orderBy");
        String order = String.valueOf(params.get("order"));

        if(orderBy != null){
            params.put("orderBy",orderBy.toString().replaceAll("\\s",""));
        }
        if(order != null){
            params.put("order",order.replaceAll("\\s",""));
        }
        return getAbstractMapper().selectByParams(params);
    }

    @Override
    public int deletes(List<String> ids) {
        return getAbstractMapper().deletes(ids);
    }

    @Override
    public int count(Map<String, Object> pagerParams) {
        return getAbstractMapper().count(pagerParams);
    }

    /**
     * 分页查询等
     * @param pagerParams
     * @return
     */
    @Override
    public List<T> selectByParams(Map<String, Object> pagerParams){
        return getAbstractMapper().selectByParams(pagerParams);
    }
}
