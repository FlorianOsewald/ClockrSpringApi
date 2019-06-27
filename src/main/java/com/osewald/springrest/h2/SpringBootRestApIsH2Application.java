package com.osewald.springrest.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.jdbc.JdbcRealm.SaltStyle;
import org.apache.shiro.realm.text.TextConfigurationRealm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
public class SpringBootRestApIsH2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestApIsH2Application.class, args);
	}

	@Bean
	public Realm realm() throws ClassNotFoundException {
		TextConfigurationRealm realm = new TextConfigurationRealm();
		realm.setCachingEnabled(false);
		String usrDef = "";
		Class.forName("org.h2.Driver");
	    try (Connection conn = DriverManager.getConnection("jdbc:h2:file:~/h2/clockrdb", "sa", ""); 
	            Statement stat = conn.createStatement()) {
	        try (ResultSet rs = stat.executeQuery("select * from user")) {
	            while (rs.next()) {
	                String username = rs.getString("username");
	                String password = rs.getString("password");
	                String rolle = rs.getString("user_rolle");
	                usrDef += username + "=" + password +  "," + rolle + "\n"; 
	            }
	            realm.setUserDefinitions(usrDef);
	            System.out.println("User Def:" + usrDef);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return realm;
	}

	@Bean
	public ShiroFilterChainDefinition shiroFilterChainDefinition() {
		DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
		//chainDefinition.addPathDefinition("/users/login", "authc");
		chainDefinition.addPathDefinition("/**", "anon");
		//chainDefinition.addPathDefinition("/users/logout", "logout");
		return chainDefinition;
	}

}
