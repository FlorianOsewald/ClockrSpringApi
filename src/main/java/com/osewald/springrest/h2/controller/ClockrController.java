package com.osewald.springrest.h2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.osewald.springrest.h2.model.Clockr;
import com.osewald.springrest.h2.model.User;
import com.osewald.springrest.h2.repo.ClockrRepository;
import com.osewald.springrest.h2.repo.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;


//@RestController
//@RequestMapping("/api")
@Component
@Path("/api")
public class ClockrController {

	
	@Autowired
	ClockrRepository repository;
	
	@Autowired
	UserRepository usrRepo;
	
	//@GetMapping("/clockr")
	@GET
	@Path("/clockr")
	public List<Clockr> getAllClockr() {
		List<Clockr> allClockrs = new ArrayList<>();
		//repository.findAll().forEach(allClockrs::add);
		repository.findAllCustom().forEach(allClockrs::add);
		return allClockrs;
	}
	
	
	//@GetMapping("/clockr/user/{user}")
	//public List<Clockr> findByUser(@PathVariable User user) {
	@GET
	@Path("/clockr/user/{userId}")
	public List<Clockr> findByUser(@PathParam("userId") long userId) {
		
		Optional<User> maybeUser = usrRepo.findById(userId);
		if(maybeUser.isPresent()) {
			User user = maybeUser.get();
			List<Clockr> clockrs = repository.findByUserCustom(user);
			return clockrs;
		}
		
		return null;
	}
	
	
	//@PostMapping(value = "/clockr/create")
	//public Clockr postClockr(@RequestBody Clockr clockr) {
	@POST
	@Path("/clockr/create")
	public Clockr postClockr(Clockr clockr) {
		Clockr _clockr = repository.save(new Clockr(clockr.getMessage(), clockr.getTime(), clockr.getUser()));
		//Switch zurück auf not @Query da Insert nicht supported
		/* repository.postClockrCustom(clockr.getMessage(), clockr.getTime(), clockr.getUser().getId());
		 Clockr _clockr = new Clockr(clockr.getMessage(), clockr.getTime(), clockr.getUser());*/
		return _clockr;
	}
}