package com.apiserver.model.mall.entity;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
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
public class MCommodity implements Serializable {

    private Long id;

    private Long categoryId;

    private Integer type;

    private String name;

    private String secondName;

    private BigDecimal price;

    private Integer views;

    private Integer status;

    private String description;

    private Date createTime;

    private Long createId;

    private Date modifyTime;

    private Long modifyId;


}