package com.apiserver.model.system.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class PageRoleButtons {

    @NotBlank(message = "菜单ID不能为空")
    @ApiModelProperty(value="菜单id")
    private String menuId;
    @NotBlank(message = "角色ID不能为空")
    @ApiModelProperty(value="角色id")
    private String roleId;

    private Long createId;
    private Long modifyId;


    @ApiModelProperty(value="列表集合")
    private List<Long> buttonsList;

}
