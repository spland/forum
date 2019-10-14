package com.shuangye;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * create by xxie
 * on 2019/10/14
 */
@SpringBootApplication
@EnableEurekaClient
public class Oauth2Server {
    public static void main(String[] args) {
        SpringApplication.run(Oauth2Server.class,args);
    }
}
