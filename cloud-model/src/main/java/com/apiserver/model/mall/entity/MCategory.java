package com.apiserver.model.mall.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Id;
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
public class MCategory implements Serializable {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="主键id")
    private Long id;

    private Integer sortId;

    private Long parentId;

    private String name;

    private String iconUrl;

    private String description;

    private Integer status;

    private Date createTime;

    private Long createId;

    private Date modifyTime;

    private Long modifyId;


}