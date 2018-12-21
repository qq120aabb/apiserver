package com.apiserver.model.system.entity;

import com.apiserver.model.system.validator.VoEmptyValidator;
import com.apiserver.model.system.validator.VoSuserValidator;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
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
@VoSuserValidator(message = "account,mobile,email三者不能同时为空")
@VoEmptyValidator(message = "账户不能为空")
public class SUsers implements Serializable {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="账户的主键id")
    private Long id;
    @ApiModelProperty(value="用户的账户")
    private String account;
    @ApiModelProperty(value="密码,使用的Md5保存")
    private String pwd;
    @ApiModelProperty(value="中文名称")
    private String chineseName;
    @ApiModelProperty(value="用户昵称")
    private String nickname;
    @ApiModelProperty(value="用户邮箱")
    private String email;
    @ApiModelProperty(value="手机号码")
    private String mobile;
    @ApiModelProperty(value="最后登录时间")
    private Date lastDate;
    @ApiModelProperty(value="性别")
    private String sex;
    @ApiModelProperty(value="出生年月")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date birthday;
    @ApiModelProperty(value="国籍")
    private String nationality;
    @ApiModelProperty(value="省份")
    private String province;
    @ApiModelProperty(value="地市")
    private String cities;
    @ApiModelProperty(value="居住地址")
    private String address;
    @ApiModelProperty(value="证件类型，数据字典")
    private Integer idType;
    @ApiModelProperty(value="证件号")
    private String idNumber;
    @ApiModelProperty(value="学历")
    private String education;
    @ApiModelProperty(value="年龄")
    private Integer age;
    @ApiModelProperty(value="户籍")
    private String household;
    @ApiModelProperty(value="民族")
    private String ethnic;
    @ApiModelProperty(value="血型")
    private String blood;
    @ApiModelProperty(value="职业")
    private String career;
    @ApiModelProperty(value="注册时间")
    private Date registerTime;

    @NotNull(message = "激活状态不能为空")
    @ApiModelProperty(value="是否激活，默认激活为：1，未激活：0，注销：2")
    private Integer status;

    @NotNull(message = "登录类型不能为空")
    @ApiModelProperty(value="登录类型，1后台登陆；2非后台登")
    private Integer loginType;

    @ApiModelProperty(value="用户皮肤样式CSS")
    private String skin;
    @ApiModelProperty(value="")
    private Date modifyTime;
    @ApiModelProperty(value="")
    private String modifyId;
    @ApiModelProperty(value="角色ID，默认角色为普通用户")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long roleId;
    @ApiModelProperty(value="头像路径")
    private String imageUrl;

    @NotNull(message = "用户类别不能为空")
    @ApiModelProperty(value="用状类别")
    private Integer userType;
    @NotNull(message = "注册类型不能为空")
    @ApiModelProperty(value="注册类型")
    private Integer registerType;
    @ApiModelProperty(value="注册码(用于邮箱注册)")
    private String registerCode;
    @ApiModelProperty(value="qq号码")
    private String qqNumber;
    @ApiModelProperty(value="微信号")
    private String wechat;
    @ApiModelProperty(value="微博号")
    private String weibo;
    @ApiModelProperty(value="微信注册时生成的ID号")
    private String openIdWechat;
    @ApiModelProperty(value="qq注册时生成的ID号")
    private String openIdQq;
    @ApiModelProperty(value="微博注册时生成的ID号")
    private String openIdWeibo;


}