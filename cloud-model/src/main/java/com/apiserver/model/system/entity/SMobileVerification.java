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
public class SMobileVerification implements Serializable {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="主键的id")
    private Long id;
    @ApiModelProperty(value="手机号码")
    private String mobile;
    @ApiModelProperty(value="验证码")
    private Integer verification;

    private Date createTime;
    @ApiModelProperty(value="类型，1 注册 2 忘记密码 3修改邮箱 4修改手机号码")
    private Integer type;
    @ApiModelProperty(value="状态：0：失效，1：有效")
    private Integer status;


}