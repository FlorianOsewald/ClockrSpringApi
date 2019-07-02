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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.osewald.springrest.h2.model.DailyEvent;
import com.osewald.springrest.h2.model.DailyEventDto;
import com.osewald.springrest.h2.model.Workday;
import com.osewald.springrest.h2.repo.DailyEventRepository;
import com.osewald.springrest.h2.repo.WorkdayRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Path("/api")
public class DailyEventController {

	@Autowired
	DailyEventRepository repository;

	@Autowired
	WorkdayRepository wdRepo;

	@GET
	@Path("/dailyEvents/workday/{workdayId}")

	public Response getEventsOfWorkday(@PathParam("workdayId") long workdayId) {
		System.out.println("Getting all Daily Daily Events: ");

		Optional<Workday> maybeWorkday = wdRepo.findById(workdayId);

		List<DailyEvent> evnts = new ArrayList<>();

		if (maybeWorkday.isPresent()) {
			Workday workday = maybeWorkday.get();
			evnts = repository.findByWorkdayCustom(workday);
		} else
			evnts = null;

		return Response.status(Status.OK).entity(evnts).build();
	}

	@POST
	@Path("/dailyEvents/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postDailyEvent(DailyEventDto dto) {
		System.out.println("Creating Daily Event: ");
		System.out.println("Daily Event: " + dto.getEvent().getEventType() + " " + dto.getEvent().getTime());
		System.out.println("Workday of Event: " + dto.getWorkday().getId());

		Optional<Workday> maybeWorkday = wdRepo.findById(dto.getWorkday().getId());

		if (maybeWorkday.isPresent()) {
			Workday workday = maybeWorkday.get();

			DailyEvent _dailyEvent = repository
					.save(new DailyEvent(dto.getEvent().getEventType(), dto.getEvent().getTime(), workday));

			return Response.status(Status.OK).entity(_dailyEvent).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

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

	@DELETE
	@Path("dailyEvents/{id}")
	public Response deleteDailyEvent(@PathParam("id") long id) {
		System.out.println("Delete dailyEvent with ID = " + id + "...");

		repository.deleteDailyEventCustom(id);

		return Response.status(Status.OK).entity("Daily Event has been deleted!").build();
	}

	@DELETE
	@Path("/dailyEvents/workday/{workdayId}")
	public Response deleteEventsOfWorkday(@PathParam("workdayId") long workdayId) {
		System.out.println("Delete All dailyEvents by Workday:" + workdayId + "...");

		Optional<Workday> maybeWorkday = wdRepo.findById(workdayId);

		if (maybeWorkday.isPresent()) {
			Workday workday = maybeWorkday.get();
			repository.deleteAllByWorkdayCustom(workday);
			return Response.status(Status.OK).entity("DailyEvents have been deleted!").build();
		} else
			return Response.status(Status.NOT_FOUND).entity("DailyEvents have not been deleted!").build();

	}

}