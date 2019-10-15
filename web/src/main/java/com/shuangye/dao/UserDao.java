package com.shuangye.dao;

import com.shuangye.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * create by xxie
 * on 2019/10/15
 */
@Repository
public interface UserDao extends JpaRepository<User,Integer> {
}
