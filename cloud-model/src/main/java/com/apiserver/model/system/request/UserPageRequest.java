package com.apiserver.model.system.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserPageRequest {

    private int pageNum=1;
    private int pageSize=20;
    @ApiModelProperty(value="账户")
    private String account;
    @ApiModelProperty(value="手机号")
    private String mobile;
    @ApiModelProperty(value="邮箱地址")
    private String email;
    @ApiModelProperty(value="是否激活，互联网用户注册，需激活为有效，邮箱注册后为0， 激活为1，默认为1，注销为2")
    private Integer status;
    @ApiModelProperty(value="登录类型：1后台登陆；2非后台登录")
    private Integer loginType;

}
