package com.shuangye.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.web.client.RestTemplate;

/**
 * create by xxie
 * on 2019/10/17
 */
@Configuration
@EnableResourceServer
public class CustomResourceServerConfigurerAdapter extends ResourceServerConfigurerAdapter {
    @Value("${spring.security.oauth2.checkTokenUrl}")
    String checkTokenUrl;
    @Value("${spring.security.oauth2.clientId}")
    String clientId;
    @Value("${spring.security.oauth2.clientSecret}")
    String clientSecret;
    @Value("${spring.security.oauth2.resourceId}")
    String resourceId;
    @Bean
    public ResourceServerTokenServices tokenServices() {
        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
        remoteTokenServices.setCheckTokenEndpointUrl(checkTokenUrl);
        remoteTokenServices.setClientId(clientId);
        remoteTokenServices.setClientSecret(clientSecret);
        remoteTokenServices.setAccessTokenConverter(accessTokenConverter());
        return remoteTokenServices;
    }

    @Bean
    public AccessTokenConverter accessTokenConverter() {
        return new DefaultAccessTokenConverter();
    }
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/user/**").permitAll();
    }
    @Override
    public void configure(ResourceServerSecurityConfigurer oauthServer)throws Exception{

        oauthServer.resourceId(resourceId).tokenServices(tokenServices());
    }
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
