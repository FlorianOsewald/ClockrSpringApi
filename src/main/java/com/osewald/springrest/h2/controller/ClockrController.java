package com.osewald.springrest.h2.controller;

import java.util.ArrayList;
import java.util.List;

import com.osewald.springrest.h2.model.Clockr;
import com.osewald.springrest.h2.model.User;
import com.osewald.springrest.h2.repo.ClockrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ClockrController {

	
	@Autowired
	ClockrRepository repository;
	
	@GetMapping("/clockr")
	public List<Clockr> getAllClockr() {
		List<Clockr> allClockrs = new ArrayList<>();
		//repository.findAll().forEach(allClockrs::add);
		repository.findAllCustom().forEach(allClockrs::add);
		return allClockrs;
	}
	
	@GetMapping("/clockr/user/{user}")
	public List<Clockr> findByUser(@PathVariable User user) {
		List<Clockr> clockrs = repository.findByUserCustom(user);
		return clockrs;
	}
	
	
	@PostMapping(value = "/clockr/create")
	public Clockr postClockr(@RequestBody Clockr clockr) {
		//Clockr _clockr = repository.save(new Clockr(clockr.getMessage(), clockr.getTime(), clockr.getUser()));
		 repository.postClockrCustom(clockr.getMessage(), clockr.getTime(), clockr.getUser().getId());
		 Clockr _clockr = new Clockr(clockr.getMessage(), clockr.getTime(), clockr.getUser());
		return _clockr;
	}
}
