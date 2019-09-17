package com.example.jwtserver.util.finalstr;

/**
 * @Author: ${朱朝阳}
 * @Date: 2019/8/14 0:04
 */

public class SystemConstant {

    public static final String JWT_ERRCODE_EXPIRE = "Token口令过期";
    public static final String JWT_ERRCODE_NOLOGIN = "用户未登录";
    public static final String JWT_ERRCODE_FAIL = "Token信息被篡改";
    public static final String JWT_SECERT = "bVqVoiX0bchqdkSpitKm";

    public static final Long JWT_TTL = 1000L * 60L * 30L;


}
