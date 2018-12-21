package com.apiserver.zuul.shiro;

import com.apiserver.kernel.result.JsonResult;
import com.apiserver.kernel.result.StatusCode;
import com.apiserver.kernel.utils.JSONUtils;
import com.apiserver.kernel.utils.TokenGenerator;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * shiro 的过滤器
 * @author jarvis
 * @date 2018-10-19
 */
public class OAuth2Filter extends AuthenticatingFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token
        String token = getRequestToken((HttpServletRequest) request);

        if(StringUtils.isBlank(token)){
            return null;
        }
        return new OAuth2Token(token);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    /**
     * 检查token
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token，如果token不存在，直接返回401
        String token = getRequestToken((HttpServletRequest) request);
        if(StringUtils.isBlank(token)){
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setContentType("application/json;charset=utf-8");
            JsonResult result=JsonResult.getResult(StatusCode.UNAUTHORIZED,"访问权限受限(Token令牌凭据缺失或已失效)");
            String json = JSONUtils.beanToJson(result);
            httpResponse.getWriter().print(json);
            return false;
        }
        return executeLogin(request, response);
    }

    /**
     * 登录失败
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        try {
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            JsonResult result=JsonResult.getResult(StatusCode.UNAUTHORIZED,throwable.getMessage());
            String json = JSONUtils.beanToJson(result);
            httpResponse.getWriter().print(json);
        } catch (Exception ee) {
        }
        return false;
    }

    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpRequest){
        //从header中获取token
        String token = httpRequest.getHeader("Authorization");
        //如果header中不存在token，则从参数中获取token
        if(StringUtils.isBlank(token)){
            token = httpRequest.getParameter("Authorization");
        }
        return token;
    }

}
