/**
 * @title: SQLService.java
 * @package com.wehealth.weiyu.common.data.dao
 * @description: TODO
 * @author hushsh
 * @date 2017年1月3日上午11:26:37
 * @version V1.0
 */

package com.apiserver.data.dao;

import com.apiserver.data.mybatis.SqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @description TODO
 * @author hs
 * @date 2017年1月3日上午11:26:37
 */
public interface SqlMapper<M> {

	@ResultMap(value = "BaseResultMap")
	@SelectProvider(method = "selectByPrimaryKey", type = SqlProvider.class)
	@Options
	M dynaSelectByPrimaryKey(M m);

	@ResultMap(value = "BaseResultMap")
	@SelectProvider(method = "selectAll", type = SqlProvider.class)
	@Options
	List<M> dynaSelectAll(M m);

	@ResultMap(value = "BaseResultMap")
	@SelectProvider(method = "selectAllLike", type = SqlProvider.class)
	@Options
	List<M> dynaSelectAllLike(@Param("t") M t, @Param("likePropertyNameList") List<String> likePropertyNameList,@Param("orderMap")  Map<String,Object> orderMap);

	@ResultMap(value = "BaseResultMap")
	@SelectProvider(method = "selectAllByOtherKeys", type = SqlProvider.class)
	@Options
	List<M> dynaSelectAllByOtherKeys(M m, List<String> propertyNameList);

	@InsertProvider(method = "insert", type = SqlProvider.class)
	@Options
	int dynaInsert(M m);

	@UpdateProvider(method = "updateByPrimaryKey", type = SqlProvider.class)
	@Options
	int dynaUpdateByPrimaryKey(M m);

	@UpdateProvider(method = "updateByOtherKeys", type = SqlProvider.class)
	@Options
	int dynaUpdateByOtherKey(M m, List<String> propertyNameList);

	@DeleteProvider(method = "deleteByPrimaryKey", type = SqlProvider.class)
	@Options
	int dynaDeleteByPrimaryKey(M m);

	@DeleteProvider(method = "delete", type = SqlProvider.class)
	@Options
	int dynaDelete(M m);

	@SelectProvider(method = "getMaxPrimaryKey", type = SqlProvider.class)
	String dynaGetMaxPrimaryKey(M m);

	@SelectProvider(method = "getMaxPrimaryKeyToNumber", type = SqlProvider.class)
	String dynaGetMaxPrimaryKeyToNumber(M m);


}
