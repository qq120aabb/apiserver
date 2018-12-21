package com.apiserver.model.system.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Id;
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
public class SRoles implements Serializable {


    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="角色的主键id")
    private Long id;
    @ApiModelProperty(value="角色的名")
    @NotBlank(message = "角色名称不能为空")
    private String name;
    @ApiModelProperty(value="角色描述")
    private String description;
    @ApiModelProperty(value="创建时间")
    private Date createTime;
    @ApiModelProperty(value="创建人id")
    private Long createId;
    @ApiModelProperty(value="修改时间")
    private Date modifyTime;
    @ApiModelProperty(value="修改人id")
    private Long modifyId;


}