package com.apiserver.kernel.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author jarvis
 * @date 2018-10-22
 */
public class TokenGenerator {

    /**
     * 过期时间设定, 24*60*60*1000 =1天
     */
    private static final long EXPIRE_TIME = 7*24*60*60*1000;
    /**
     * 私钥
     */
    private static final String SECRET="5f12f400-2d98-11e8-9be1-00163e0c3e80";

    /**
     * 校验token是否正确
     * @param token jwt的token
     * @param username 用户名
     * @param uid 用户的唯一id
     * @return 是否正确
     */
    public static boolean verify(String token, String username,Long uid) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("microHealth")
                    .withSubject(username)
                    .withClaim("uid", uid)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (UnsupportedEncodingException exception) {
            return false;
        } catch (JWTVerificationException exception){
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getSubject();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
    public static Long getUserId(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("uid").asLong();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名,指定时间后过期
     * @param username 用户名
     * @param uid 用户的ID
     * @return 加密的token
     */
    public static String sign(String username, Long uid) {
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            //附带username信息
            return JWT.create()
                    .withIssuer("microHealth")
                    .withSubject(username)
                    .withClaim("uid", uid)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
