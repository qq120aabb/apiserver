/**
 * @title: s.java
 * @package com.wehealth.weiyu.common.core.annotation
 * @description: TODO
 * @author hushsh
 * @date 2016年12月30日下午4:23:23
 * @version V1.0
 */

package com.apiserver.data.annotation;

/**
 * @description 主键ID生成策略
 * @author hs
 * @date 2016年12月30日下午4:23:23
 */
public enum IdStrategy {
	/**
	 * 生成18个字节的随机数子作为主键id 取当前时间戳，然后通过位移运算等手段生成18字节的随机数
	 */
	TIMESTAMP_CREATOR,
	/**
	 * 将自然数作为主键id 1,2,3,4,5,6,7,8,9,10,11,12,13
	 */
	CONTINUOUS_CREATOR,

	/**
	 * 生成无限节点的类似树形结构的主键id, 使用该策略必须要确定根节点的主键id位数,后续子节点的主键id只会是根节点主键id的倍数, 例如:
	 * 根节点id确定为0001 长度为5, 那么同级节点下一个id则为0002 而0001下的字节点id则为00010001,
	 * 而00010001下的第一个子节点为000100010001 0001 0002 0003 00010001 00010002
	 * 000100010001 000100010002
	 */
	TREE_CREATOR,

	/**
	 * 生成固定长度的类似树形结构的主键id 使用该策略必须确定id的最大长度和根节点长度,之后主键id无论怎样添加都不会大于该长度 例如:
	 * 假设id最大长度为6，根节点长度为2的话，根id为110000,那么下几个同级节点id则为120000,130000,140000,150000......
	 * 110000 110100 110101 120000 120100 120101 120102 120200 120201
	 */
	FIXED_TREE_CREATOR,
	
	/**
	 * 忽略
	 */
	IGNORE;
	
}