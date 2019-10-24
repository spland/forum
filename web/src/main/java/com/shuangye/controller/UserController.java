package com.shuangye.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shuangye.dao.UserDao;
import com.shuangye.entity.User;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

/**
 * create by xxie
 * on 2019/10/15
 */
@Controller
public class UserController {
    @Value("${spring.security.oauth2.getTokenUrl}")
    String getTokenUrl;
    @Value("${spring.security.oauth2.checkTokenUrl}")
    String checkTokenUrl;
    @Autowired
    private UserDao userDao;
    @Autowired
    RestTemplate restTemplate;
    @RequestMapping("/getUser")
    @ResponseBody
    public User getUser(){
        Optional<User> users = userDao.findById(1);
        if (users.isPresent()){
            User user = users.get();
            return user;
        }else {
            return null;
        }
    }
    @RequestMapping("/user/deng")
    public String ddd(HttpServletRequest request){

        return "login";
    }
    @RequestMapping("/user/regist")
    public String register(){

        return "register";
    }
    @RequestMapping("/user/news")
    public String news(){

        return "news";
    }
    @RequestMapping("/user/login")
    public String login(@PathParam("username") String username,@PathParam("password")String password, HttpServletResponse resp) throws IOException {
        String url = getTokenUrl;
        HttpResponse response = getRemoteResponse(url);
        if (response.getStatusLine().getStatusCode() == 200) {
            InputStream body = response.getEntity().getContent();
            String data = getStringFromInputStream(body);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode actualObj = mapper.readTree(data);
            String token = actualObj.get("access_token").toString();
            resp.addHeader("access_token", token);
        }
        return "index";

    }
    private HttpResponse getRemoteResponse(String url) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Authorization", "Basic Zm9ydW1fY2xpZW50OnVzZXI=");
        org.apache.http.client.HttpClient httpClient = HttpClients.createDefault();
        //设置请求和传输超时时间,单位毫秒
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
        httpPost.setConfig(requestConfig);
        HttpResponse response = httpClient.execute(httpPost);
        return response;
    }
    private static String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
