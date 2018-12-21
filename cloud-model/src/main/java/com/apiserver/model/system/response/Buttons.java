package com.apiserver.model.system.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Buttons {
    @NotBlank(message = "字典ID不能为空")
    @ApiModelProperty(value="字典的id")
    private String dictId;
    @ApiModelProperty(value="字典的名称，key")
    private String dictName;
    @ApiModelProperty(value="字典的值，value")
    private String dictValue;

    @ApiModelProperty(value="0未选中 1选中")
    private int isChecked;
}
