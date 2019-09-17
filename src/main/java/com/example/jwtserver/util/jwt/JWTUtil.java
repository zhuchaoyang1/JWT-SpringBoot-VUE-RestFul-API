package com.example.jwtserver.util.jwt;


import com.example.jwtserver.util.finalstr.SystemConstant;
import com.example.jwtserver.util.pojo.JWTCheckResult;
import io.jsonwebtoken.*;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ${朱朝阳}
 * @Date: 2019/8/13 23:06
 */

public class JWTUtil {
    /**
     * 签发JWT
     *
     * @param id
     * @param subject   可以是JSON数据 尽可能少
     * @param ttlMillis
     * @return String
     */
    public static String createJWT(String id, String subject, Map<String, Object> claims, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey secretKey = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                // 主题
                .setSubject(subject)
                // 业务逻辑相关在Map中，存储在负载中
                .setClaims(claims)
                // 签发者
                .setIssuer("kedacom_system")
                // 签发时间
                .setIssuedAt(now)
                // 签名算法以及密匙
                .signWith(signatureAlgorithm, secretKey);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date expDate = new Date(expMillis);
            // 过期时间
            builder.setExpiration(expDate);
        }
        return builder.compact();
    }

    /**
     * 验证JWT
     *
     * @param jwtStr
     * @return
     */
    public static JWTCheckResult validateJWT(String jwtStr) {
        JWTCheckResult checkResult = new JWTCheckResult();
        Claims claims = null;
        try {
            claims = parseJWT(jwtStr);
            checkResult.setSuccess(true);
            checkResult.setClaims(claims);
        } catch (ExpiredJwtException e) {
            // JWT过期异常  该异常在科达统一异常管理处管理
            checkResult.setErrCode(SystemConstant.JWT_ERRCODE_EXPIRE);
            checkResult.setSuccess(false);


            /**
             * 既然现在游客也有
             *
             * 现在要处理的是：
             * 一、已经登录过的账户当过期时如何处理？
             *      答：返回一个码，前端看到这个码就直接进入登录页面并提示登录超期
             * 二、游客一直没有登录过但是如果游客Token也超期了怎么办？总不能强制让其登录吧！
             *      答：在这个地方刷新一下并发到前端，前端保存，这个时候前端发起一次retry即可
             */




        } catch (SignatureException e) {
            // JWT载荷或者前面被篡改异常   该异常在科达统一异常管理处管理
            checkResult.setErrCode(SystemConstant.JWT_ERRCODE_FAIL);
            checkResult.setSuccess(false);
        } catch (JwtNullException e) {
            /**
             * JWT为空异常
             * 这个地方构造假Token
             */
            Map<String, Object> claimsMap = new HashMap<>();
            String tourist = JWTUtil.createJWT("", "用户Token", claimsMap, SystemConstant.JWT_TTL);
            checkResult.setTouristToken(tourist);
            checkResult.setSuccess(true);
        } catch (MalformedJwtException e) {
            // JWT头被篡改异常
            checkResult.setErrCode(SystemConstant.JWT_ERRCODE_FAIL);
            checkResult.setSuccess(false);
        }
        return checkResult;
    }

    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.decode(SystemConstant.JWT_SECERT);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 解析JWT字符串
     * 如果传入的JWT是null
     * 那么会有异常，下面处理异常即可，科达项目中是统一异常管理
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws JwtNullException {
        SecretKey secretKey = generalKey();
        if (jwt == null) {
            throw new JwtNullException(SystemConstant.JWT_ERRCODE_NOLOGIN);
        }
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

}
