package com.osewald.springrest.h2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.osewald.springrest.h2.model.Clockr;
import com.osewald.springrest.h2.model.User;
import com.osewald.springrest.h2.repo.ClockrRepository;
import com.osewald.springrest.h2.repo.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;



@Component
@Path("/api")
public class ClockrController {

	
	@Autowired
	ClockrRepository repository;
	
	@Autowired
	UserRepository usrRepo;
	

	@GET
	@Path("/clockr")
	public Response getAllClockr() {
		List<Clockr> allClockrs = new ArrayList<>();
		repository.findAllCustom().forEach(allClockrs::add);
		return Response.status(Status.OK).entity(allClockrs).build();
	}
	
	

	@GET
	@Path("/clockr/user/{userId}")
	public Response findByUser(@PathParam("userId") long userId) {
		
		Optional<User> maybeUser = usrRepo.findById(userId);
		if(maybeUser.isPresent()) {
			User user = maybeUser.get();
			List<Clockr> clockrs = repository.findByUserCustom(user);
			return Response.status(Status.OK).entity(clockrs).build();
		}
		
		return Response.status(Status.NOT_FOUND).build();
	}
	
	

	@POST
	@Path("/clockr/create")
	public Response postClockr(Clockr clockr) {
		Clockr _clockr = repository.save(new Clockr(clockr.getMessage(), clockr.getTime(), clockr.getUser()));
		return Response.status(Status.OK).entity(_clockr).build();
	}
}