package com.osewald.springrest.h2.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.osewald.springrest.h2.controller.ClockrController;
import com.osewald.springrest.h2.controller.DailyEventController;
import com.osewald.springrest.h2.controller.UserController;
import com.osewald.springrest.h2.controller.WorkdayController;

/*
public class RestConfig extends Application {
	public Set<Class<?>> getClasses() {
		return new HashSet<Class<?>>(Arrays.asList(ClockrController.class, DailyEventController.class,
				UserController.class, WorkdayController.class));
	}
} */
@Component
public class RestConfig extends ResourceConfig {
	public RestConfig() {
		register(UserController.class);
		register(DailyEventController.class);
		register(WorkdayController.class);
		register(ClockrController.class);
	}
}
