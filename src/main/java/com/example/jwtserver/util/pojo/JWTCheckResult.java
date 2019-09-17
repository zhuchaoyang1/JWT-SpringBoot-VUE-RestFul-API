package com.example.jwtserver.util.pojo;

import io.jsonwebtoken.Claims;
import lombok.Data;

/**
 * @Author: ${朱朝阳}
 * @Date: 2019/8/14 0:07
 */
@Data
public class JWTCheckResult {

    private boolean success;
    private Claims claims;
    private String errCode;

    /**
     * 游客Token 用于保存游客Token
     */
    private String touristToken;

}
