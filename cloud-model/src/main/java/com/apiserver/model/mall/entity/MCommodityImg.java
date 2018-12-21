package com.apiserver.model.mall.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
/**
 * @author jarvis
 * @date 2018-12-05
 */
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MCommodityImg implements Serializable {
    private Long id;

    private Integer sortId;

    private Long commodityId;

    private String imgUrl;

    private Integer type;

    private Date createTime;

    private Long createId;

    private Date modifyTime;

    private Long modifyId;

}