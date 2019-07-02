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
import javax.ws.rs.core.Response;

import com.osewald.springrest.h2.model.Workday;
import com.osewald.springrest.h2.repo.WorkdayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
@Path("/api")
public class WorkdayController {
	
	@Autowired
	WorkdayRepository repository;
	
	

	@GET
	@Path("/workdays")
	public List<Workday> getAllWorkdays() {
		List<Workday> workdays = new ArrayList<>();
		repository.findAll().forEach(workdays::add);
		
		return workdays;
	}


	@GET
	@Path("/workdays/username/{username}")
	public Response getTodayWorkdayByUser(@PathParam("username") String username) {
		System.out.println("Trying to retrieve workday Today by User: " + username + "!");
		Workday wd = repository.getWorkdayTodayUsername(username);
		
		return Response.status(Response.Status.OK).entity(wd).build();
	}
	


	@GET
	@Path("/workdays/{username}")
	public Response findAllWorkdaysByUser(@PathParam("username") String username) {
		List<Workday> wds = new ArrayList<>();
		repository.findByUsernameCustom(username).forEach(wds::add);
	
		return Response.status(Response.Status.OK).entity(wds).build();
	}
	


	@PUT
	@Path("/workdays/{id}")
	public Response updateWorkday(@PathParam("id") long id, Workday workday) {	
		System.out.println("Update Workday with ID = " + id + "...");
		

		Optional<Workday> workdayData = repository.findByIdCustom(id);
		
		
		if(workdayData.isPresent()) {
			Workday _workday = workdayData.get();
			_workday.setId(workday.getId());
			_workday.setUsername(workday.getUsername());
			_workday.setDate(workday.getDate());
			repository.updateWorkdayCustom(_workday.getId(), _workday.getDate(), _workday.getUsername());
			return Response.status(Response.Status.OK).entity(new Workday(_workday.getDate(), _workday.getUsername())).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
	
	

	@POST
	@Path("/workdays/create")
	public Response postWorkday(Workday workday) {
		
		Date tmp = new Date(new java.util.Date().getTime());

		System.out.println("Creating Workday. Template workday date: " + tmp);
		
		Workday _workday = repository.save(new Workday(tmp, workday.getUsername()));
		return Response.status(Response.Status.OK).entity(_workday).build();
	}
	
	

	@DELETE
	@Path("/workdays/{id}")
	public Response deleteWorkday(@PathParam("id") long id) {
	    System.out.println("Delete Workday with ID = " + id + "...");
	 
	    repository.deleteWorkdayCustom(id);
	 
	    return Response.status(Response.Status.OK).entity("Workday has been deleted!").build();
	  }

}