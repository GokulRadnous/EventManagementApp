package com.eventmanagement.controller;

import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eventmanagement.dto.ResponseStructure;
import com.eventmanagement.entity.Attendee;
import com.eventmanagement.entity.Event;
import com.eventmanagement.service.EventService;

@RestController
@RequestMapping("/ema/event")
public class EventController {

	@Autowired
	private EventService eventService;

	@PostMapping
	public ResponseEntity<ResponseStructure<Event>> saveEvent(@RequestBody Event event) {
		return eventService.saveEvent(event);
	}

	@GetMapping
	public ResponseEntity<ResponseStructure<List<Event>>> getAllEvents() {
		return eventService.getAllEvent();
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Event>> getEventById(@PathVariable int id) {
		return eventService.findById(id);
	}

	@PutMapping
	public ResponseEntity<ResponseStructure<Event>> updateEvent(@RequestBody Event event) {
		return eventService.updateEvent(event);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteEvent(@PathVariable int id) {
		return eventService.deleteEvent(id);
	}

	@GetMapping("/events/{id}/attendees")
	public ResponseEntity<ResponseStructure<List<Attendee>>> getAttendees(@PathVariable int id) {
		return eventService.getAttendeesByEventId(id);
	}

	@GetMapping("/organizers/{organizerId}/events")
	public ResponseEntity<ResponseStructure<List<Event>>> getEventsByOrganizer(@PathVariable int organizerId) {
		return eventService.getEventsByOrganizerId(organizerId);
	}

	@GetMapping("/pagination/{pagenumber}/{pagesize}/{field}")
	public ResponseEntity<ResponseStructure<List<Event>>> getEventByPaginationAndSorting(@PathVariable int pagenumber,
			@PathVariable int pagesize, @PathVariable String field) {
		return eventService.getEventByPaginationAndSorting(pagenumber, pagesize, field);
	}
}
