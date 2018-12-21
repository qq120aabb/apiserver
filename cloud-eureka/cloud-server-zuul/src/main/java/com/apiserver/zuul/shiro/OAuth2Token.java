package com.apiserver.zuul.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author jarvis
 * @data 2018-3-21
 * 令牌token的定义
 */
public class OAuth2Token implements AuthenticationToken{

    private static final long serialVersionUID = 1L;

    private String token;

    public OAuth2Token(String token){
        this.token=token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
