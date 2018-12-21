package com.apiserver.model.system.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class RoleMenuRequest {
    @NotNull(message = "角色ID不能为空")
    @ApiModelProperty(value="角色的主键id")
    private Long roleId;
    @ApiModelProperty(value="创建人id")
    private Long createId;
    @ApiModelProperty(value="菜单的list集合")
    private List<Long> menuIds;
}
