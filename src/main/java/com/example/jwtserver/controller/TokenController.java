package com.example.jwtserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ${朱朝阳}
 * @Date: 2019/8/13 22:12
 */
@RestController
@RequestMapping("/token")
public class TokenController {

    @GetMapping("/noLogin")
    public String tokenIsNull() {
        return "no login";
    }

}
