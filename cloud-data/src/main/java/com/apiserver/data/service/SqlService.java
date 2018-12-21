/**
 * @title: SqlService.java
 * @package com.wehealth.weiyu.common.data.service
 * @description: TODO
 * @author hushsh
 * @date 2017年1月3日上午11:34:30
 * @version V1.0
 */

package com.apiserver.data.service;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @description TODO
 * @author hs
 * @date 2017年1月3日上午11:34:30
 */
public interface SqlService<M> {

	M dynaSelectByPrimaryKey(M m) ;

	List<M> dynaSelectAllByOtherKeys(M m, List<String> propertyNameList);

	List<M> dynaSelectAllLike(M m, List<String> likePropertyNameList,Map<String,Object> orderMap);

	List<M> dynaSelectAll(M m);

	int dynaInsert(M m);

	int dynaUpdateByPrimaryKey(M m) ;

	int dynaUpdateByOtherKey(M m, List<String> propertyNameList);

	int dynaDeleteByPrimaryKey(M m) ;

	int dynaDelete(M m);

	String dynaGetMaxPrimaryKey(M m);

	String dynaGetMaxPrimaryKeyToNumber(M m);

	 M fetchOneByParameters(Map<String, Object> queryMap);

	List<M> fetchByParameters(Map<String, Object> queryMap, Map<String, Object> orderMap);


	int fetchByAllOrParameters(Map<String, Object> queryMap);
}