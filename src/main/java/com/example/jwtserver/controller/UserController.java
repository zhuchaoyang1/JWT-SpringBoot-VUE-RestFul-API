package com.example.jwtserver.controller;

import com.example.jwtserver.pojo.User;
import com.example.jwtserver.service.IUserService;
import com.example.jwtserver.util.finalstr.SystemConstant;
import com.example.jwtserver.util.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author: ${朱朝阳}
 * @Date: 2019/8/12 23:16
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/save")
    public String saveUser(@RequestBody User user) {
        userService.save(user);
        return "ok";
    }

    @PostMapping("/login")
    public Map<Object, Object> login(@RequestBody User user) {
        Map<Object, Object> map = new HashMap<>(2);

        List<User> list = userService.queryUser(user.getUserName(), user.getPassword());

        if (list.size() > 0) {
            user = list.get(0);
            Map<String, Object> claims = new HashMap<>(3);
            claims.put("appid", user.getId());
            claims.put("username", user.getUserName());
            claims.put("password", user.getPassword());
            map.put("token", JWTUtil.createJWT(String.valueOf(user.getId()), "用户Token", claims, SystemConstant.JWT_TTL));
        }

        return map;
    }

    @GetMapping("/tests")
    public String test(HttpServletRequest request) {
        Long appId = 1L;

        //=============================以下代码封装==========================
        Object objUserId = request.getAttribute("appid");
        Object tourToken = request.getAttribute("tourtoken");
        if (tourToken != null) {
            // 代表我目前是游客
            // 根据业务需要是否需要跳转到登录界面  这里展示不需要
            return String.valueOf(tourToken);
        }

//        Long appId = Long.parseLong("" + request.getAttribute("appid"));
        return "您经过了登录检测，您的ID为：" + appId;
    }


}
