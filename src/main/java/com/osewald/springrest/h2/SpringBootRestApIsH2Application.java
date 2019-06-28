package com.osewald.springrest.h2;

import com.osewald.springrest.h2.auth.ClockRRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootRestApIsH2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRestApIsH2Application.class, args);
    }

    @Bean
    public Realm realm() {
        return new ClockRRealm();
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/api/users/login", "anon");
        chainDefinition.addPathDefinition("/api/users/logout", "logout");
        chainDefinition.addPathDefinition("/**", "anon");
        return chainDefinition;
    }

}
