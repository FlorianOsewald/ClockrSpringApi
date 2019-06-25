package com.osewald.springrest.h2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SpringBootRestApIsH2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestApIsH2Application.class, args);
	}
	/*
	@ExceptionHandler(AuthorizationException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public String handleException(AuthorizationException e, Model model) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", HttpStatus.FORBIDDEN.value());
		map.put("message", "No message available");
		model.addAttribute("errors", map);

		return "error";
	}

	@Bean
	public Realm realm() {
		TextConfigurationRealm realm = new TextConfigurationRealm();
		realm.setUserDefinitions("joe.coder=password,user\n" + "jill.coder=password,admin");

		realm.setRoleDefinitions("admin=read,write\n" + "user=read");
		realm.setCachingEnabled(true);
		return realm;
	}

	@Bean
	public ShiroFilterChainDefinition shiroFilterChainDefinition() {
		DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
		//chainDefinition.addPathDefinition("/users/login", "authc");
		//chainDefinition.addPathDefinition("/logout", "logout");
		return chainDefinition;
	}

	@ModelAttribute(name = "subject")
	public Subject subject() {
		return SecurityUtils.getSubject();
	}
	
	@Bean
	public CacheManager cacheManager() {
	    return new MemoryConstrainedCacheManager();
	} */
}
