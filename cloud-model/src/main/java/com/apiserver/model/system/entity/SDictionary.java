package com.apiserver.model.system.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

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
public class SDictionary implements Serializable {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="主键id")
    private Long id;
    @ApiModelProperty(value="排序的id")
    private Integer sortId;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="父级id")
    private Long parentId;
    @ApiModelProperty(value="字典的名称，key")
    private String name;
    @ApiModelProperty(value="字典的值，value")
    private String value;
    @ApiModelProperty(value="字典的描述")
    private String description;

    private Long createId;

    private Date createTime;

    private Long modifyId;

    private Date modifyTime;

    @ApiModelProperty(value="字典的状态，1：生效，0：失效")
    private Integer status;


}