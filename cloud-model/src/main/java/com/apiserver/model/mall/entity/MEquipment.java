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
public class MEquipment implements Serializable {

    private Long id;

    private Integer sortId;

    private Long commodityId;

    private String type;

    private String deviceId;

    private String company;

    private Long companyId;

    private String mac;

    private Integer status;

    private Integer objectType;

    private Long objectId;

    private Date createTime;

    private Long createId;

    private Date modifyTime;

    private Long modifyId;

    private Integer batchNumber;


   }