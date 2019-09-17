package com.example.jwtserver.pojo;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author: ${朱朝阳}
 * @Date: 2019/8/12 22:31
 */

@Entity
@Data
@Table(name = "sys_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String password;

}
