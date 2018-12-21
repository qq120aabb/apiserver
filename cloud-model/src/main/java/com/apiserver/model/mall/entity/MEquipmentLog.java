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
public class MEquipmentLog implements Serializable {

    private Long id;

    private Long equipmentId;

    private Integer operateType;

    private String description;

    private Date createTime;

    private Long createId;

    private Date modifyTime;

    private Long modifyId;

}