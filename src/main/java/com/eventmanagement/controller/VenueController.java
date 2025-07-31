package com.eventmanagement.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.eventmanagement.dto.ResponseStructure;
import com.eventmanagement.entity.Event;
import com.eventmanagement.entity.Venue;
import com.eventmanagement.service.VenueService;

@RestController
@RequestMapping("/ema/venue")
public class VenueController {
	@Autowired
	private VenueService venueService;

	@PostMapping
	public ResponseEntity<ResponseStructure<Venue>> saveVenue(@RequestBody Venue venue) {
		return venueService.saveVenue(venue);
	}

	@GetMapping
	public ResponseEntity<ResponseStructure<List<Venue>>> getAllVenue() {
		return venueService.getAllVenue();
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Venue>> findById(@PathVariable int id) {
		return venueService.findById(id);
	}

	@PutMapping
	public ResponseEntity<ResponseStructure<Venue>> updateById(@RequestBody Venue venue) {
		return venueService.updateById(venue);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteVenue(@PathVariable int id) {
		return venueService.deleteVenue(id);
	}

	@GetMapping("/venues/{venueId}/events")
	public ResponseEntity<ResponseStructure<List<Event>>> getEventsByVenueId(@PathVariable int venueId) {
		return venueService.getEventsByVenueId(venueId);
	}

	@GetMapping("/location/{location}")
	public ResponseEntity<ResponseStructure<List<Venue>>> getVenueByLocation(@PathVariable String location) {
		return venueService.getVenueByLocation(location);
	}

	@GetMapping("/pagination/{pagenumber}/{pagesize}/{feild}")
	public ResponseEntity<ResponseStructure<List<Venue>>> getVenueByPaginationAndSorting(@PathVariable int pagenumber,
			@PathVariable int pagesize, @PathVariable String feild) {
		return venueService.getVenueByPaginationAndSorting(pagenumber, pagesize, feild);
	}
}
