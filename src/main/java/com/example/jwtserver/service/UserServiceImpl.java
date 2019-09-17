package com.example.jwtserver.service;

import com.example.jwtserver.dao.UserDao;
import com.example.jwtserver.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: ${朱朝阳}
 * @Date: 2019/8/12 23:00
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public List<User> queryUser(String username, String password) {
        return userDao.findByUserNameAndPassword(username,password);
    }

}
