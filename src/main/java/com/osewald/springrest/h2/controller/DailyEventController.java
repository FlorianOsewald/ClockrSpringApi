package com.osewald.springrest.h2.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.osewald.springrest.h2.model.DailyEvent;
import com.osewald.springrest.h2.model.DailyEventDto;
import com.osewald.springrest.h2.model.Workday;
import com.osewald.springrest.h2.repo.DailyEventRepository;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class DailyEventController {
	
	@Autowired
	DailyEventRepository repository;
	
	@GetMapping("/dailyEvents/workday/{workday}")
	public List<DailyEvent> getEventsOfWorkday(@PathVariable Workday workday) {
		//List<DailyEvent> evnts = repository.findByWorkday(workday);
		List<DailyEvent> evnts = repository.findByWorkdayCustom(workday);
		return evnts;
	}
	
	
	@PostMapping("/dailyEvents/create")
	public DailyEvent postDailyEvent(@RequestBody DailyEventDto dto) {
		System.out.println("Creating Daily Event: ");
		System.out.println("Daily Event: " + dto.getEvent().getEventType() + " " + dto.getEvent().getTime());
		System.out.println("Workday of Event: " + dto.getWorkday().getId());
		//DailyEvent _dailyEvent = repository.save(new DailyEvent(dto.getEvent().getEventType(), dto.getEvent().getTime(), dto.getWorkday()));
		repository.postDailyEventCustom(dto.getEvent().getTime(), dto.getEvent().getEventType(), dto.getWorkday());
		DailyEvent _dailyEvent = new DailyEvent(dto.getEvent().getEventType(), dto.getEvent().getTime(), dto.getWorkday());
		return _dailyEvent;
	}
	
	@PutMapping("dailyEvents/{id}")
	public ResponseEntity<DailyEvent> updateDailyEvent(@PathVariable("id") long id, @RequestBody DailyEvent dailyEvent) {
		Optional<DailyEvent> dailyEventData = repository.findById(id);
		
		if(dailyEventData.isPresent()) {
			DailyEvent _dailyEvent = dailyEventData.get();
			_dailyEvent.setEventType(dailyEvent.getEventType());
			_dailyEvent.setTime(dailyEvent.getTime());
			_dailyEvent.setWorkday(dailyEvent.getWorkday());
			return new ResponseEntity<>(repository.save(_dailyEvent), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@DeleteMapping("dailyEvents/{id}")
	@Transactional
	public ResponseEntity<String> deleteDailyEvent(@PathVariable("id") long id) {
		System.out.println("Delete dailyEvent with ID = " + id + "...");
		 
	    //repository.deleteById(id);
		repository.deleteDailyEventCustom(id);
		
	    return new ResponseEntity<>("DailyEvent has been deleted!", HttpStatus.OK);
	}
	
	@DeleteMapping("/dailyEvents/workday/{workday}")
	@Transactional
	public ResponseEntity<String> deleteEventsOfWorkday(@PathVariable Workday workday) {
		System.out.println("Delete All dailyEvents by Workday:" + workday + "...");
		
		//repository.deleteAllByWorkday(workday);
		
		repository.deleteAllByWorkdayCustom(workday);
		
		return new ResponseEntity<>("DailyEvents have been deleted!", HttpStatus.OK);
	}

}
