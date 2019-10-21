package com.shuangye.service;

import com.shuangye.dao.UserDao;
import com.shuangye.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * create by xxie
 * on 2019/10/15
 */
@Service
public class JdbcDetailsService implements UserDetailsService {
    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User byUsername = userDao.findByUsername(s);
        if (byUsername != null){
            return (UserDetails)userDao.findByUsername(s);
        }else {
            throw new UsernameNotFoundException("User" + s + "can not be found");
        }

    }
}
