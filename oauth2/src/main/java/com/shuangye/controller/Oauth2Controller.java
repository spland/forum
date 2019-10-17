package com.shuangye.controller;

import com.shuangye.dao.UserDao;
import com.shuangye.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * create by xxie
 * on 2019/10/14
 */
@RestController
public class Oauth2Controller {
    @Autowired
    UserDao userDao;
    @RequestMapping("/getName")
    @PreAuthorize("hasRole('USER')")
    public String getName(){
        Optional<User> daoById = userDao.findById(1);
        if (daoById.isPresent()){
           return daoById.get().getUsername();
        }else {
            return "error";
        }
    }
}
