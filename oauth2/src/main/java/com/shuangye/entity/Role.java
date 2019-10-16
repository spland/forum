package com.shuangye.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * create by xxie
 * on 2019/10/16
 */
@Entity
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;
    @Column
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
