package com.example.jwtserver.service;

import com.example.jwtserver.pojo.User;

import java.util.List;

/**
 * @Author: ${朱朝阳}
 * @Date: 2019/8/12 22:58
 */

public interface IUserService  {

    void save(User user);

    List<User> queryUser(String username,String password);

}
