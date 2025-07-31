package com.eventmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eventmanagement.dao.VenueDao;
import com.eventmanagement.dto.ResponseStructure;
import com.eventmanagement.entity.Event;
import com.eventmanagement.entity.Venue;
import com.eventmanagement.exception.IdNotFoundException;
import com.eventmanagement.exception.NoRecordAvailableException;

@Service
public class VenueService {
	@Autowired
	private VenueDao venueDao;

	public ResponseEntity<ResponseStructure<Venue>> saveVenue(Venue venue) {
		Venue savedVenue = venueDao.saveVenue(venue);

		ResponseStructure<Venue> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Venue saved successfully");
		response.setData(savedVenue);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<List<Venue>>> getAllVenue() {
		List<Venue> venues = venueDao.getAllVenue();

		if (!venues.isEmpty()) {
			ResponseStructure<List<Venue>> response = new ResponseStructure<>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("All venues retrieved successfully");
			response.setData(venues);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		throw new NoRecordAvailableException("Database is empty. No records found.");
	}

	public ResponseEntity<ResponseStructure<Venue>> findById(int id) {
		Optional<Venue> venue = venueDao.findById(id);

		if (venue.isPresent()) {
			ResponseStructure<Venue> response = new ResponseStructure<>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Venue found successfully");
			response.setData(venue.get());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		throw new NoRecordAvailableException("Venue with ID " + id + " not found.");
	}

	public ResponseEntity<ResponseStructure<Venue>> updateById(Venue venue) {
		Optional<Venue> updated = venueDao.updateVenue(venue);

		if (updated.isPresent()) {
			ResponseStructure<Venue> response = new ResponseStructure<>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Venue updated successfully");
			response.setData(updated.get());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		throw new NoRecordAvailableException("Venue with ID " + venue.getId() + " not found.");
	}

	public ResponseEntity<ResponseStructure<String>> deleteVenue(int id) {
		Optional<Venue> opt = venueDao.findById(id);

		if (opt.isPresent()) {
			venueDao.deleteVenue(opt.get());

			ResponseStructure<String> response = new ResponseStructure<>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Venue record deleted successfully");
			response.setData("Success");

			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		throw new IdNotFoundException("Venue ID " + id + " not available in the database");
	}

	public ResponseEntity<ResponseStructure<List<Event>>> getEventsByVenueId(int venueId) {
		List<Event> events = venueDao.getEventsByVenueId(venueId);

		if (!events.isEmpty()) {
			ResponseStructure<List<Event>> response = new ResponseStructure<>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Events retrieved for venue ID: " + venueId);
			response.setData(events);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		throw new NoRecordAvailableException("No events found for venue ID: " + venueId);
	}

	public ResponseEntity<ResponseStructure<List<Venue>>> getVenueByLocation(String location) {
		List<Venue> venues = venueDao.getVenueByLocation(location);

		if (!venues.isEmpty()) {
			ResponseStructure<List<Venue>> response = new ResponseStructure<>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Venues retrieved for location: " + location);
			response.setData(venues);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		throw new NoRecordAvailableException("No venues found at location: " + location);
	}

	public ResponseEntity<ResponseStructure<List<Venue>>> getVenueByPaginationAndSorting(int pageNumber, int pageSize,
			String field) {
		Page<Venue> venuePage = venueDao.getVenueByPaginationAndSorting(pageNumber, pageSize, field);

		if (!venuePage.isEmpty()) {
			ResponseStructure<List<Venue>> response = new ResponseStructure<>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Retrieved venues sorted by field: " + field);
			response.setData(venuePage.getContent());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		throw new NoRecordAvailableException("No venues found for page " + pageNumber + " with size " + pageSize);
	}

}
