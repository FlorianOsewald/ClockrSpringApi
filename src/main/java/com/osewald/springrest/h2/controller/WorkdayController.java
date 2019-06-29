package com.osewald.springrest.h2.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.osewald.springrest.h2.model.Workday;
import com.osewald.springrest.h2.repo.WorkdayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

//@RestController
//@RequestMapping("/api")
@Component
@Path("/api")
public class WorkdayController {
	
	@Autowired
	WorkdayRepository repository;
	
	
	//@GetMapping("/workdays")
	@GET
	@Path("/workdays")
	public List<Workday> getAllWorkdays() {
		List<Workday> workdays = new ArrayList<>();
		repository.findAll().forEach(workdays::add);
		
		return workdays;
	}
	
	//@GetMapping("/workdays/username/{username}")
	//public Workday getTodayWorkdayByUser(@PathVariable String username) {
	@GET
	@Path("/workdays/username/{username}")
	public Workday getTodayWorkdayByUser(@PathParam("username") String username) {
		System.out.println("Trying to retrieve workday Today by User: " + username + "!");
		Workday wd = repository.getWorkdayTodayUsername(username);
		System.out.println("WD RESULT: " + wd.getDate());
		return wd;
		
	}
	
	//@GetMapping("/workdays/{username}")
	//public List<Workday> findAllWorkdaysByUser(@PathVariable String username) {
	@GET
	@Path("/workdays/{username}")
	public List<Workday> findAllWorkdaysByUser(@PathParam("username") String username) {
		List<Workday> wds = new ArrayList<>();
		repository.findByUsernameCustom(username).forEach(wds::add);
	
		return wds;
	}
	
	//@PutMapping("/workdays/{id}")
	//public ResponseEntity<Workday> updateWorkday(@PathVariable("id") long id, @RequestBody Workday workday) {
	@PUT
	@Path("/workdays/{id}")
	public ResponseEntity<Workday> updateWorkday(@PathParam("id") long id, Workday workday) {	
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
	
	
	//@PostMapping("/workdays/create")
	//public Workday postWorkday(@RequestBody Workday workday) {
	@POST
	@Path("/workdays/create")
	public Workday postWorkday(Workday workday) {
		
		Date tmp = new Date(new java.util.Date().getTime());

		System.out.println("Creating Workday. Template workday date: " + tmp);
		
		//repository.postWorkdayCustom(workday.getDate(), workday.getUsername());
		//Workday _workday = new Workday(workday.getDate(), workday.getUsername());
		//Switch wieder auf Std-Query da Insert nicht supported in @Query 
		Workday _workday = repository.save(new Workday(tmp, workday.getUsername()));
		return _workday;
	}
	
	
	//@DeleteMapping("/workdays/{id}")
	@DELETE
	@Path("/workdays/{id}")
	public ResponseEntity<String> deleteWorkday(@PathParam("id") long id) {
	    System.out.println("Delete Workday with ID = " + id + "...");
	 
	    //repository.deleteById(id);
	    repository.deleteWorkdayCustom(id);
	 
	    return new ResponseEntity<>("Workday has been deleted!", HttpStatus.OK);
	  }

}