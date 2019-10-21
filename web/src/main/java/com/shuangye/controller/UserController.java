package com.shuangye.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shuangye.dao.UserDao;
import com.shuangye.entity.User;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import sun.net.www.http.HttpClient;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * create by xxie
 * on 2019/10/15
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Value("${spring.security.oauth2.getTokenUrl}")
    String getTokenUrl;
    @Autowired
    private UserDao userDao;
    @Autowired
    RestTemplate restTemplate;
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
    @RequestMapping("/deng")
    public String ddd(){
        return "login";
    }
    @RequestMapping("/login2")
    public String login(@PathParam("username") String username,@PathParam("password")String password) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Basic Zm9ydW1fY2xpZW50OnVzZXI=");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("grant_type","password");
        map.add("username",username);
        map.add("password",password);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(getTokenUrl, request, String.class);
        String token = "";
        if (response.getStatusCodeValue()==200){
            String body = response.getBody();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode actualObj = mapper.readTree(body);
            token = actualObj.get("access_token").toString();
        }else {
            token = "用户名或密码错误";
        }
        return "index";

    }
}
