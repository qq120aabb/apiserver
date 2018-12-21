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
public class SPageRole implements Serializable {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="主键id")
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="菜单的id")
    private Long menuId;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="角色的id")
    private Long roleId;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="字典的id")
    private Long dictionaryId;

    private Long createId;

    private Date createTime;

    private Long modifyId;

    private Date modifyTime;


}