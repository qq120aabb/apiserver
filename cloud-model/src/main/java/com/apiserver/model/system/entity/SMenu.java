package com.apiserver.model.system.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
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
public class SMenu implements Serializable {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="主键id")
    private Long id;
    @ApiModelProperty(value="排序的id")
    private Integer sortId;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="菜单的父级id")
    private Long parentId;

    @NotBlank(message = "菜单名称不能为空")
    @ApiModelProperty(value="菜单的名称")
    private String name;

    @NotBlank(message = "菜单url不能为空")
    @ApiModelProperty(value="菜单的URl")
    private String url;
    @ApiModelProperty(value="菜单的图标icon")
    private String iconClass;
    @ApiModelProperty(value="菜单的描述")
    private String description;

    private Long createId;

    private Date createTime;

    private Long modifyId;

    private Date modifyTime;

    @ApiModelProperty(value="是否选中")
    @Transient
    private Integer checked;
}