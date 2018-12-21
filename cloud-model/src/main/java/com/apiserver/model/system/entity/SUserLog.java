package com.apiserver.model.system.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Id;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
public class SUserLog implements Serializable {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="主键id")
    private Long id;
    @ApiModelProperty(value="登录使用的用的账户")
    private String account;
    @ApiModelProperty(value="用户的主键ID")
    private Long userId;
    @ApiModelProperty(value="登录操作的时间")
    private Date logTime;
    @ApiModelProperty(value="操作的描述")
    private String operation;
    @ApiModelProperty(value="登录操作的类型，1登录，2退出，3其他")
    private Integer type;
    @ApiModelProperty(value="获取的登录IP，代理可能获取不准")
    private String userIp;
    @ApiModelProperty(value="是否成功的标记，1：代表成功，0：代表失败")
    private Integer successFlag;
    @ApiModelProperty(value="登陆的平台：1、后台，2、app，3、微信")
    private Integer loginPlatform;
}