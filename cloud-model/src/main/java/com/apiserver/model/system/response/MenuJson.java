package com.apiserver.model.system.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class MenuJson {
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="菜单主键id")
    private Long id;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="父级id")
    private Long parentId;
    @ApiModelProperty(value="菜单名称")
    private String label;
    @ApiModelProperty(value="排序的id")
    private Integer sort;
    @ApiModelProperty(value="是否选中")
    private Integer checked;
    @ApiModelProperty(value="url 路径")
    private String url;
    @ApiModelProperty(value="样式css 的icon")
    private String iconClass;
    @ApiModelProperty(value="子元素")
    private List<MenuJson> children;

}
