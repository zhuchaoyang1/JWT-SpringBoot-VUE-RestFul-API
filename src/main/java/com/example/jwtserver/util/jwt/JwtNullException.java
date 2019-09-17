package com.example.jwtserver.util.jwt;

/**
 * 自定义异常
 * 防止Token为null时出现的Exception异常导致解析中其他异常为Exception异常处理
 *
 * @Author: ${朱朝阳}
 * @Date: 2019/8/15 22:39
 */

public class JwtNullException extends IllegalArgumentException {
    /**
     * 错误信息
     */
    private String message;

    public JwtNullException() {
        super();
    }

    public JwtNullException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
