package com.eventmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eventmanagement.dao.EventDao;
import com.eventmanagement.dto.ResponseStructure;
import com.eventmanagement.entity.Attendee;
import com.eventmanagement.entity.Event;
import com.eventmanagement.entity.Organizer;
import com.eventmanagement.entity.Venue;
import com.eventmanagement.exception.IdNotFoundException;
import com.eventmanagement.exception.NoRecordAvailableException;
import com.eventmanagement.repository.OrganizerRepository;
import com.eventmanagement.repository.VenueRepository;

@Service
public class EventService {
	@Autowired
	private EventDao eventDao;

	@Autowired
	private OrganizerRepository organizerRepository;

	@Autowired
	private VenueRepository venueRepository;

	public ResponseEntity<ResponseStructure<Event>> saveEvent(Event event) {

		Integer organizerId = event.getOrganizer().getId();
		Integer venueId = event.getVenue().getId();

		Optional<Organizer> opt1 = organizerRepository.findById(organizerId);
		Optional<Venue> opt2 = venueRepository.findById(venueId);

		if (opt1.isPresent() && opt2.isPresent()) {
			event.setOrganizer(opt1.get());
			event.setVenue(opt2.get());

			Event saved = eventDao.save(event);

			ResponseStructure<Event> response = new ResponseStructure<>();
			response.setStatusCode(HttpStatus.CREATED.value());
			response.setMessage("Event record is saved");
			response.setData(saved);

			return new ResponseEntity<>(response, HttpStatus.CREATED);
		}

		throw new IdNotFoundException("Invalid Organizer ID or Venue ID. Cannot save Event.");
	}

	public ResponseEntity<ResponseStructure<List<Event>>> getAllEvent() {
		List<Event> eventList = eventDao.getAllEvent();

		if (!eventList.isEmpty()) {
			ResponseStructure<List<Event>> response = new ResponseStructure<>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("All event records retrieved successfully");
			response.setData(eventList);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		throw new NoRecordAvailableException("Database is empty. No event records found.");
	}

	public ResponseEntity<ResponseStructure<Event>> findById(int id) {
		Optional<Event> event = eventDao.findById(id);
		if (event.isPresent()) {
			ResponseStructure<Event> response = new ResponseStructure<>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Event record with ID " + id + " retrieved successfully");
			response.setData(event.get());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		throw new IdNotFoundException("Record with ID " + id + " was not found in the database.");
	}

	public ResponseEntity<ResponseStructure<Event>> updateEvent(Event event) {
		Optional<Event> existing = eventDao.findById(event.getId());

		if (existing.isPresent()) {
			Event updatedEvent = eventDao.save(event);

			ResponseStructure<Event> response = new ResponseStructure<>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Event record updated successfully");
			response.setData(updatedEvent);

			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		throw new IdNotFoundException("ID " + event.getId() + " not found in the database");
	}

	public ResponseEntity<ResponseStructure<String>> deleteEvent(int id) {
		Optional<Event> opt = eventDao.findById(id);

		if (opt.isPresent()) {
			eventDao.deleteEvent(opt.get());

			ResponseStructure<String> response = new ResponseStructure<>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Event record deleted successfully");
			response.setData("Success");

			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		throw new IdNotFoundException("ID " + id + " not available in the database");
	}

	public ResponseEntity<ResponseStructure<List<Attendee>>> getAttendeesByEventId(int eventId) {
		List<Attendee> attendees = eventDao.getAttendeeByEventId(eventId);

		if (!attendees.isEmpty()) {
			ResponseStructure<List<Attendee>> response = new ResponseStructure<>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Attendees for event ID " + eventId + " retrieved successfully");
			response.setData(attendees);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		throw new NoRecordAvailableException("No attendees found for event ID " + eventId);
	}

	public ResponseEntity<ResponseStructure<List<Event>>> getEventsByOrganizerId(int organizerId) {
		List<Event> events = eventDao.getEventsByOrganizerId(organizerId);

		if (!events.isEmpty()) {
			ResponseStructure<List<Event>> response = new ResponseStructure<>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Events by organizer ID " + organizerId + " retrieved successfully");
			response.setData(events);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		throw new NoRecordAvailableException("No events found for organizer ID " + organizerId);
	}

	public ResponseEntity<ResponseStructure<List<Event>>> getEventByPaginationAndSorting(int pageNumber, int pageSize,
			String sortBy) {
		Page<Event> eventPage = eventDao.getEventByPaginationAndSorting(pageNumber, pageSize, sortBy);

		if (!eventPage.isEmpty()) {
			ResponseStructure<List<Event>> response = new ResponseStructure<>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Retrieved events sorted by field: " + sortBy);
			response.setData(eventPage.getContent());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		throw new NoRecordAvailableException("No events found for page " + pageNumber + " with size " + pageSize);
	}
}
