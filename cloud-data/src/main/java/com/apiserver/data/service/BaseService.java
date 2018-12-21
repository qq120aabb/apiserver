package com.apiserver.data.service;


import com.apiserver.data.dao.BaseMapper;
import com.apiserver.data.mybatis.model.EntityTable;
import com.apiserver.data.mybatis.util.EntityUtil;

import com.apiserver.kernel.exception.BusinessException;
import com.apiserver.kernel.result.StatusCode;
import com.apiserver.kernel.utils.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.*;

@Slf4j
public abstract class BaseService<D extends BaseMapper<T>, T> implements SqlService<T> {


    protected D dao;

    @Autowired
    public void setDao(D dao) {
        this.dao = dao;
    }

    private Class<T> entityClass;

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {
        entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];
    }


    public T fetchOneByParameters(Map<String, Object> queryMap) {
        return dao.getOneByParameters(mapperQueryMap(queryMap, entityClass));
    }
    public int fetchByAllOrParameters(Map<String, Object> queryMap){
        return dao.fetchByAllOrParameters(mapperQueryMap(queryMap, entityClass));
    }
    public List<T> fetchByParameters(Map<String, Object> queryMap,
                                     Map<String, Object> orderMap) {
        return dao.getByParameters(mapperQueryMap(queryMap, entityClass),
                mapperOrderMap(orderMap, entityClass));
    }

    public Map<String, Object> mapperQueryMap(Map<String, Object> queryMap,
                                              Class<?> clazz) {
        return mapperQueryMap(queryMap, clazz, false);
    }

    public Map<String, Object> mapperOrderMap(Map<String, Object> orderMap,
                                              Class<?> clazz) {
        if (null == orderMap) {
            return null;
        }
        Map<String, Object> newMap = null;
        if (orderMap instanceof LinkedHashMap) {
            newMap = new LinkedHashMap<String, Object>();
        } else {
            newMap = new HashMap<String, Object>();
        }
        for (Map.Entry<String, Object> entry : orderMap.entrySet()) {
            String key = entry.getKey();
            String value = (String) entry.getValue();
            Field filed = getDeclaredField(clazz, key);
            if (null != filed) {
                String filedName = filed.getName();
                filedName = getColumnName(filedName);
                newMap.put(filedName, value.toUpperCase());
            } else {
                // newMap.put(key, value.toUpperCase());
            }
        }
        orderMap = null;
        return newMap;
    }
    /**
     *
     * @description TODO
     * @author hushsh
     * @param queryMap
     * @param clazz
     * @param isGetOther
     *            是否获取不在映射的类之外的数据，默认false
     * @return
     */
    public Map<String, Object> mapperQueryMap(Map<String, Object> queryMap,
                                              Class<?> clazz, boolean isGetOther) {
        if (null == queryMap) {
            return null;
        }
        Map<String, Object> newMap = new HashMap<String, Object>();
        for (Map.Entry<String, Object> entry : queryMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            Field filed = getDeclaredField(clazz, key);
            if (null != filed) {
                String filedName = filed.getName();
                filedName = getColumnName(filedName);
                if (filed.getGenericType().toString().equals("boolean")|| filed.getGenericType().toString().equals("class java.lang.Boolean")) {
                    if (Boolean.valueOf(value.toString())) {
                        newMap.put(filedName, Integer.valueOf(1));
                    } else {
                        newMap.put(filedName, Integer.valueOf(0));
                    }
                } else if(filed.getGenericType().toString().equals("String")|| filed.getGenericType().toString().equals("class java.lang.String")){
                    newMap.put(filedName, "'"+value+"'");
                }else {
                    newMap.put(filedName, value);
                }
            } else {
                if (isGetOther)
                    newMap.put(key.toUpperCase(), value);
            }
        }
        queryMap = null;
        return newMap;
    }

    private Field getDeclaredField(Class<?> clazz, String name) {
        Field field = null;
        for (Field f : getAllFields(clazz, null)) {
            if (f.getName().equals(name)) {
                field = f;
                break;
            }
        }
        return field;
    }
    private List<Field> getAllFields(Class<?> clazz, List<Field> fields) {
        if (null == fields) {
            fields = new ArrayList<Field>();
        }
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        }
        return fields;
    }
    private static String getColumnName(final String name) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i != 0) {
                    buffer.append("_");
                }
            }
            buffer.append(c);
        }
        return buffer.toString().toLowerCase();
    }

    ////
    @Override
    public T dynaSelectByPrimaryKey(T m) throws BusinessException {
        EntityTable entityTable = EntityUtil.getEntityTable(m);
        for (String primaryKey : entityTable.getPrimaryKeys()) {
            if (null == entityTable.getParams().get(primaryKey))
                return null;
//                throw new BusinessException(StatusCode.getEnum(600));
        }
        return dao.dynaSelectByPrimaryKey(m);
    }

    @Override
    public List<T> dynaSelectAll(T m) {
        return dao.dynaSelectAll(m);
    }

    @Override
    public List<T> dynaSelectAllLike(T m, List<String> likePropertyNameList,Map<String,Object> orderMap) {
        return dao.dynaSelectAllLike(m, likePropertyNameList,orderMap);
    }

    @Override
    public List<T> dynaSelectAllByOtherKeys(T m,
                                            List<String> propertyNameList) {
        return dao.dynaSelectAllByOtherKeys(m, propertyNameList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int dynaInsert(T m) {
        SnowFlake snowFlake = new SnowFlake(2, 3);
        long id = snowFlake.nextId();
        EntityTable entity = EntityUtil.getEntityTable(m);
        String propertyName = entity.getProperties().get(entity.getPrimaryKeys().get(0));
        try {
            BeanUtils.setProperty(m, propertyName, id);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error(e.getMessage(), e);
        }

        return dao.dynaInsert(m) ;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int dynaUpdateByPrimaryKey(T m) {
        EntityTable entityTable = EntityUtil.getEntityTable(m);
        for (String primaryKey : entityTable.getPrimaryKeys()) {
            if (null == entityTable.getParams().get(primaryKey))
//				throw exceptionBuilder.bulideException(null, "主键的值不能为空");
                log.error("主键ID不能为空");
        }
        return dao.dynaUpdateByPrimaryKey(m);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int dynaUpdateByOtherKey(T m, List<String> propertyNameList) {
        return dao.dynaUpdateByOtherKey(m, propertyNameList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int dynaDeleteByPrimaryKey(T m) {
        EntityTable entityTable = EntityUtil.getEntityTable(m);
		for (String primaryKey : entityTable.getPrimaryKeys()) {
			if (null == entityTable.getParams().get(primaryKey))
//                throw new BusinessException(StatusCode.getEnum(600));
			    log.error("主键ID不能为空");
		}
        return dao.dynaDeleteByPrimaryKey(m);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int dynaDelete(T m) {
        return dao.dynaDelete(m);
    }

    @Override
    public String dynaGetMaxPrimaryKey(T m) {
        return dao.dynaGetMaxPrimaryKey(m);
    }

    @Override
    public String dynaGetMaxPrimaryKeyToNumber(T m) {
        return dao.dynaGetMaxPrimaryKeyToNumber(m);
    }


}
