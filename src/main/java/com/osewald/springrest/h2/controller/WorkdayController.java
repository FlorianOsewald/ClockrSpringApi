package com.osewald.springrest.h2.controller;

import java.sql.Date;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.osewald.springrest.h2.repo.WorkdayRepository;
import com.osewald.springrest.h2.model.Workday;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class WorkdayController {
	
	@Autowired
	WorkdayRepository repository;
	
	
	@GetMapping("/workdays")
	public List<Workday> getAllWorkdays() {
		List<Workday> workdays = new ArrayList<>();
		repository.findAll().forEach(workdays::add);
		
		return workdays;
	}
	
	@GetMapping("/workdays/username/{username}")
	public Workday getTodayWorkdayByUser(@PathVariable String username) {
		System.out.println("Trying to retrieve workday Today by User: " + username + "!");
		Workday wd = repository.getWorkdayTodayUsername(username);
		return wd;
		
	}
	
	@GetMapping("/workdays/{username}")
	public List<Workday> findAllWorkdaysByUser(@PathVariable String username) {
		List<Workday> wds = new ArrayList<>();
		repository.findByUsernameCustom(username).forEach(wds::add);
	
		return wds;
	}
	
	@PutMapping("/workdays/{id}")
	public ResponseEntity<Workday> updateWorkday(@PathVariable("id") long id, @RequestBody Workday workday) {
		System.out.println("Update Workday with ID = " + id + "...");
		
		//Optional<Workday> workdayData = repository.findById(id);
		Optional<Workday> workdayData = repository.findByIdCustom(id);
		
		
		if(workdayData.isPresent()) {
			Workday _workday = workdayData.get();
			_workday.setUsername(workday.getUsername());
			_workday.setDate(workday.getDate());
			//return new ResponseEntity<>(repository.save(_workday), HttpStatus.OK);
			repository.postWorkdayCustom(_workday.getDate(), _workday.getUsername());
			return new ResponseEntity<>(new Workday(_workday.getDate(), _workday.getUsername()), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/workdays/create")
	public Workday postWorkday(@RequestBody Workday workday) {
		
		Date tmp = new Date(new java.util.Date().getTime());

		System.out.println("Creating Workday. Template workday date: " + tmp);
		
		repository.postWorkdayCustom(workday.getDate(), workday.getUsername());
		Workday _workday = new Workday(workday.getDate(), workday.getUsername());
		//Workday _workday = repository.save(new Workday(tmp, workday.getUsername()));
		return _workday;
	}
	
	
	@DeleteMapping("/workdays/{id}")
	public ResponseEntity<String> deleteWorkday(@PathVariable("id") long id) {
	    System.out.println("Delete Workday with ID = " + id + "...");
	 
	    //repository.deleteById(id);
	    repository.deleteWorkdayCustom(id);
	 
	    return new ResponseEntity<>("Workday has been deleted!", HttpStatus.OK);
	  }

}
