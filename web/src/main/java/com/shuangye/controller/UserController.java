package com.shuangye.controller;

import com.shuangye.dao.UserDao;
import com.shuangye.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * create by xxie
 * on 2019/10/15
 */
@RestController

public class UserController {
    @Autowired
    private UserDao userDao;
    @RequestMapping("/getUser")
    public User getUser(){
        Optional<User> users = userDao.findById(1);
        if (users.isPresent()){
            User user = users.get();
            return user;
        }else {
            return null;
        }

    }
}
