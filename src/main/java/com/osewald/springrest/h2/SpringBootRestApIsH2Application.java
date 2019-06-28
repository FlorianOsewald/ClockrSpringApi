package com.osewald.springrest.h2;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.osewald.springrest.h2.auth.ClockRRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.PassThruAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.util.WebUtils;
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

    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {

        final ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/api/users/login");

        final Map<String, String> chainDefinition = new HashMap<>();
        chainDefinition.put("/api/users/logout", "logout");
        chainDefinition.put("/**", "pass-through");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(chainDefinition);

        final PassThruAuthenticationFilter ptFilter = new PassThruAuthenticationFilter() {

            @Override
            protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
                if (isLoginRequest(request, response)) {
                    return true;
                } else {
                    // fail-fast instead of redirecting to login page
                    WebUtils.toHttp(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return false;
                }
            }
        };
        shiroFilterFactoryBean.getFilters().put("pass-through", ptFilter);

        return shiroFilterFactoryBean;
    }

}
