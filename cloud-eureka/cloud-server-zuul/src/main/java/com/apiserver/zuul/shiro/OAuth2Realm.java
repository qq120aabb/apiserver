package com.apiserver.zuul.shiro;

import com.apiserver.kernel.utils.TokenGenerator;
import com.apiserver.model.system.entity.SUsers;
import com.apiserver.zuul.api.UsersInterface;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 授权领域范围
 * @author jarvis
 * 2018-10-18
 */
public class OAuth2Realm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * feign 的远程调用，获取用户身份
     */
    @Autowired
    UsersInterface usersInterface;

    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权(验证权限时调用)
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        Long userId = ShiroUtils.getUserId();
        /**
         *设计权限系统后,需要重写
         */
        Set<String> rolesSet = new HashSet(Arrays.asList(new String[]{"view","edit"}));
        Set<String> permsSet = new HashSet(Arrays.asList(new String[]{"admin"}));

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(rolesSet);
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        /**
         * 解密获得username,和user_id
         */
        String username = TokenGenerator.getUsername(token);
        Long userId= TokenGenerator.getUserId(token);

        if (username == null) {
            throw new AuthenticationException("token invalid");

        }

        if (! TokenGenerator.verify(token,username,userId)) {
            throw new AuthenticationException("UserId or token error");
        }

        //查询用户信息，若用户被锁定，token在有效期内，也可以强制踢掉
        SUsers user=usersInterface.get(userId);
        //账号锁定
        if(user.getStatus() == 0){
         throw new LockedAccountException("账号已被锁定,请联系管理员");
        }
        return new SimpleAuthenticationInfo(user, token, getName());
    }


}
