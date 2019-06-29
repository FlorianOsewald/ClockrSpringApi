package com.osewald.springrest.h2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import com.osewald.springrest.h2.model.DailyEvent;
import com.osewald.springrest.h2.model.DailyEventDto;
import com.osewald.springrest.h2.model.Workday;
import com.osewald.springrest.h2.repo.DailyEventRepository;
import com.osewald.springrest.h2.repo.WorkdayRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/*
@RestController
@RequestMapping("/api")
*/
@Component
@Path("/api")
public class DailyEventController {

	@Autowired
	DailyEventRepository repository;

	@Autowired
	WorkdayRepository wdRepo;

	// @GetMapping("/dailyEvents/workday/{workday}")
	// public List<DailyEvent> getEventsOfWorkday(@PathVariable Workday workday) {
	@GET
	@Path("/dailyEvents/workday/{workdayId}")
	// public List<DailyEvent> getEventsOfWorkday(@PathParam("workday") Workday
	// workday) {
	public List<DailyEvent> getEventsOfWorkday(@PathParam("workdayId") long workdayId) {
		System.out.println("Getting all Daily Daily Events: ");
		// List<DailyEvent> evnts = repository.findByWorkday(workday);
		Optional<Workday> maybeWorkday = wdRepo.findById(workdayId);

		List<DailyEvent> evnts = new ArrayList<>();

		if (maybeWorkday.isPresent()) {
			Workday workday = maybeWorkday.get();
			evnts = repository.findByWorkdayCustom(workday);
		} else
			evnts = null;

		return evnts;
	}

	// @PostMapping("/dailyEvents/create")
	// public DailyEvent postDailyEvent(@RequestBody DailyEventDto dto) {
	@POST
	@Path("/dailyEvents/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public DailyEvent postDailyEvent(DailyEventDto dto) {
		System.out.println("Creating Daily Event: ");
		System.out.println("Daily Event: " + dto.getEvent().getEventType() + " " + dto.getEvent().getTime());
		System.out.println("Workday of Event: " + dto.getWorkday().getId());

		// Zurück zum originalen Code, da Insert in Custom Queries nicht unterstützt
		// sind.
		DailyEvent _dailyEvent = repository
				.save(new DailyEvent(dto.getEvent().getEventType(), dto.getEvent().getTime(), dto.getWorkday()));
		return _dailyEvent;
	}

	// @PutMapping("dailyEvents/{id}")
	@PUT
	@Path("dailyEvents/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public ResponseEntity<DailyEvent> updateDailyEvent(@PathParam("id") long id, DailyEvent dailyEvent) {
		Optional<DailyEvent> dailyEventData = repository.findById(id);

		if (dailyEventData.isPresent()) {
			DailyEvent _dailyEvent = dailyEventData.get();
			_dailyEvent.setEventType(dailyEvent.getEventType());
			_dailyEvent.setTime(dailyEvent.getTime());
			_dailyEvent.setWorkday(dailyEvent.getWorkday());
			return new ResponseEntity<>(repository.save(_dailyEvent), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	// @DeleteMapping("dailyEvents/{id}")
	@DELETE
	@Path("dailyEvents/{id}")
	public ResponseEntity<String> deleteDailyEvent(@PathParam("id") long id) {
		System.out.println("Delete dailyEvent with ID = " + id + "...");

		// repository.deleteById(id);
		repository.deleteDailyEventCustom(id);

		return new ResponseEntity<>("DailyEvent has been deleted!", HttpStatus.OK);
	}

	// @DeleteMapping("/dailyEvents/workday/{workday}")
	@DELETE
	@Path("/dailyEvents/workday/{workdayId}")
	// public ResponseEntity<String> deleteEventsOfWorkday(@PathParam("workday")
	// Workday workday) {
	public ResponseEntity<String> deleteEventsOfWorkday(@PathParam("workdayId") long workdayId) {
		System.out.println("Delete All dailyEvents by Workday:" + workdayId + "...");

		// repository.deleteAllByWorkday(workday);

		Optional<Workday> maybeWorkday = wdRepo.findById(workdayId);

		if (maybeWorkday.isPresent()) {
			Workday workday = maybeWorkday.get();
			repository.deleteAllByWorkdayCustom(workday);
			return new ResponseEntity<>("DailyEvents have been deleted!", HttpStatus.OK);
		} else
			return new ResponseEntity<>("DailyEvents have not been deleted!", HttpStatus.NOT_FOUND);

	}

}