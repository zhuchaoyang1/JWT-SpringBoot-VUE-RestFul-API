package com.example.jwtserver.util.netInterceptor;

import com.example.jwtserver.util.jwt.JWTUtil;
import com.example.jwtserver.util.pojo.JWTCheckResult;
import io.jsonwebtoken.Claims;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: ${朱朝阳}
 * @Date: 2019/7/18 12:28
 */
@Configuration
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        boolean flag = true;
        Object appIdsObj = null;

        String requestUrl = request.getRequestURI();

        if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
            // 所有的跨域OPTIONS请求直接放行

        } else {
            if (requestUrl.equals("/user/login")) {
                // 登录请求直接放行

            } else {
                String jwtToken = request.getHeader("jwt-token");
                /**
                 * 测试看看异常统一处理之后，下面代码是否执行
                 * 我估计应该不会，因为科达项目中的没有经过try{} catch(Exception e){}的处理而是集中管理
                 * 且在ControllerAdviced的注解下，直接返回前端
                 */
                JWTCheckResult jwtCheckResult = JWTUtil.validateJWT(jwtToken);
                if (jwtCheckResult != null && jwtCheckResult.isSuccess()) {
                    Claims claims = jwtCheckResult.getClaims();
                    appIdsObj = claims == null ? null : claims.get("appid");
                    if(claims == null){
                        // 将游客Token放入请求中带入Controller并传递给前端保存
                        servletRequest.setAttribute("tourtoken",jwtCheckResult.getTouristToken());
                    }
                } else {
                    // 这里只是为了看到测试  其实实际工作中还是需要ErrorCode中再去细化
                    flag = false;
                }
            }
        }

        if (flag) {
            servletRequest.setAttribute("appid", appIdsObj);
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            response.sendRedirect("/token/noLogin");
        }
    }

    @Override
    public void destroy() {

    }
}
