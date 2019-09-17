package com.example.jwtserver.dao;

import com.example.jwtserver.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @Author: ${朱朝阳}
 * @Date: 2019/8/12 22:56
 */

public interface UserDao extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    List<User> findByUserNameAndPassword(String username,String password);

}
