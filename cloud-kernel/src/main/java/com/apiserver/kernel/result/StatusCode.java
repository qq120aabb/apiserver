package com.apiserver.kernel.result;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jarvis
 * @date 2018-3-20
 */
public enum StatusCode {

    OK(200, "服务器成功返回用户请求的数据."),
    //用户新建或修改数据成功
    CREATED(201,"CREATED"),
    ACCEPTED(202,"表示一个请求已经进入后台排队."),
    NO_CONTENT(204 ,"用户删除数据成功."),
    INVALID_REQUEST(400,"用户发出的请求有错误，服务器没有进行新建或修改数据的操作."),
    UNAUTHORIZED(401,"访问权限受限(Token令牌凭据缺失或已失效)"),
    NOT_FOUND(404,"发出的请求成功，但服务器没有查到数据.（没有符合条件的数据或启用了熔断）"),
    Not_ACCEPTABLE(406,"请求的格式不正确."),
    SERVER_ERROR(500, "服务器发生错误，无法判断发出的请求是否成功."),
    TokenTimeOut(501,"token超时过期"),

    //主键ID不能为空
    primaryKey_IdNotNull(600,"主键ID不能为空"),
    //用户的token不正确
    NotAuthorization(4000,"用户的token不正确"),
    //未找到登陆的账户名
    NotFindAccount(4001,"未找到登陆的账户名"),
    //该账号输入的密码错误
    PassWordError(4002,"登陆账户的密码不正确"),
    //该账号未激活
    AccountUnActive(4003,"该账号未激活"),
    //该账号已经存在,请使用其他账号注册
    AccountAlreadyExists(4004,"该账号已经存在,请使用其他账号注册"),
    //验证码错误,请输入正确的验证码
    VerificationCodeError(4005,"验证码错误,请输入正确的验证码"),
    //验证码已过期请重新请求
    VerificationCodeExpired(4006,"验证码已过期,请重新请求"),
    //该账户号未找到对应验证码
    NotFindVerificationCode(4007,"该账户号未找到对应验证码"),
    //密码不符合规则
    PasswordRule(4008,"密码不符合规则"),
    //验证码请求次数过多
    smsExceedingLimit(4009,"验证码请求次数过多"),
    //验证码请求60秒只能发送一次
    smsFailure(4010,"验证码请求60秒只能发送一次"),
    //存在相同的子字典配置
    ExistChildrenDict(4011,"存在相同的子字典配置"),
    //非后台账户尝试登陆
    NoPermissionByConsole(4012,"非后台账户尝试登陆"),
    ModifiedSuccess(4013,"修改密码成功"),
    OldPasswordMatching(4014,"旧密码匹配不成功"),
    NonExistentUser(4015,"用户信息不存在"),
    DictionaryValueAlreadyExists(4016,"同一组内，数据字典的值不能重复"),


    //======角色相关=======
    RoleNameExists(4020,"角色名已存在"),


    ;

    private static final Map<Integer, StatusCode> CODE_MAP = new HashMap<Integer, StatusCode>();

    static {
        for (StatusCode typeEnum : StatusCode.values()) {
            CODE_MAP.put(typeEnum.getCode(), typeEnum);
        }
    }
    StatusCode(Integer code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }
    public Integer getCode() {
        return code;
    }

    public String getMeaning() {
        return meaning;
    }

    public static StatusCode getEnum(Integer code) {

        return CODE_MAP.get(code);
    }
    private final Integer code;
    private final String meaning;

    public static StatusCode valueOf(Integer code) {
        if (code == null) {
            throw new IllegalArgumentException("valueOf - param code is null.");
        }
        StatusCode result = null;
        for (StatusCode statusCodeEnum : StatusCode.values()) {
            if (statusCodeEnum == null) {
                continue;
            }
            if (code.equals(statusCodeEnum.getCode())) {
                result = statusCodeEnum;
                break;
            }
        }
        if (result == null) {
            throw new IllegalStateException("can not find the enum item with code " + code);
        }
        return result;
    }
}
