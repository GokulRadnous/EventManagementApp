package com.eventmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eventmanagement.dao.RegistrationDao;
import com.eventmanagement.dto.ResponseStructure;
import com.eventmanagement.entity.Attendee;
import com.eventmanagement.entity.Event;
import com.eventmanagement.entity.Organizer;
import com.eventmanagement.entity.Registration;
import com.eventmanagement.entity.Venue;
import com.eventmanagement.exception.IdNotFoundException;
import com.eventmanagement.exception.NoRecordAvailableException;
import com.eventmanagement.repository.AttendeeRepository;
import com.eventmanagement.repository.EventRepository;

@Service
public class RegistrationService {

	@Autowired
	private RegistrationDao registrationDao;

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private AttendeeRepository attendeeRepository;

	public ResponseEntity<ResponseStructure<Registration>> createRegistration(Registration registration) {
	    Integer eventId = registration.getEvent().getId();
	    Integer attendeeId = registration.getAttendee().getId();

	    Optional<Event> opt1 = eventRepository.findById(eventId);
	    Optional<Attendee> opt2 = attendeeRepository.findById(attendeeId);

	    if (opt1.isPresent() && opt2.isPresent()) {
	        registration.setEvent(opt1.get());
	        registration.setAttendee(opt2.get());

	        Registration saved = registrationDao.saveRegistration(registration);

	        ResponseStructure<Registration> response = new ResponseStructure<>();
	        response.setStatusCode(HttpStatus.CREATED.value());
	        response.setMessage("Registration record is saved");
	        response.setData(saved);

	        return new ResponseEntity<>(response, HttpStatus.CREATED);
	    }

	    throw new IdNotFoundException("Event ID or Attendee ID is invalid. Registration failed.");
	}

	public ResponseEntity<ResponseStructure<List<Registration>>> getAllRegistration() {
		List<Registration> registrations = registrationDao.getAllRegistrations();

		if (!registrations.isEmpty()) {
			ResponseStructure<List<Registration>> response = new ResponseStructure<>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("All Registrations retrieved successfully");
			response.setData(registrations);
			return new ResponseEntity<>(response, HttpStatus.OK); 
		}

		throw new NoRecordAvailableException("Database is empty. No registration records found.");
	}

	public ResponseEntity<ResponseStructure<Registration>> findById(int id) {
		Optional<Registration> registration = registrationDao.findById(id);

		if (registration.isPresent()) {
			ResponseStructure<Registration> response = new ResponseStructure<>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Registration record with ID " + id + " retrieved successfully");
			response.setData(registration.get());

			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		throw new IdNotFoundException("Registration record with ID " + id + " was not found in the database.");
	}

	public ResponseEntity<ResponseStructure<String>> cancelRegistration(int id) {
		Optional<Registration> opt = registrationDao.findById(id);

		if (opt.isPresent()) {
			registrationDao.deleteRegistration(opt.get());

			ResponseStructure<String> response = new ResponseStructure<>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Registration with ID " + id + " cancelled successfully");
			response.setData("Success");

			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		throw new IdNotFoundException("Registration ID " + id + " not available in the database");
	}

	public ResponseEntity<ResponseStructure<List<Registration>>> getRegistrationsByEventId(Integer eventId) {
        List<Registration> registrations = registrationDao.getRegistrationByEventId(eventId);

        if (registrations == null || registrations.isEmpty()) {
            throw new NoRecordAvailableException("No registrations found for event ID " + eventId);
        }

        ResponseStructure<List<Registration>> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Registrations fetched successfully");
        response.setData(registrations);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

	public ResponseEntity<ResponseStructure<List<Registration>>> getRegistrationByAttendee(int id) {
		List<Registration> registrations = registrationDao.getRegistrationByAttendee(id);

		if (!registrations.isEmpty()) {
			ResponseStructure<List<Registration>> response = new ResponseStructure<>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Registrations for Attendee ID " + id + " retrieved successfully");
			response.setData(registrations);

			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		throw new NoRecordAvailableException("No registrations found for Attendee ID " + id);
	}

	public ResponseEntity<ResponseStructure<List<Registration>>> getRegistrationByPaginationAndSorting(int pageNumber, int pageSize, String field) {
	    Page<Registration> registrationPage = registrationDao.getRegistrationByPaginationAndSorting(pageNumber, pageSize, field);

	    if (!registrationPage.isEmpty()) {
	        ResponseStructure<List<Registration>> response = new ResponseStructure<>();
	        response.setStatusCode(HttpStatus.OK.value());
	        response.setMessage("Retrieved registrations sorted by field: " + field);
	        response.setData(registrationPage.getContent());
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	    throw new NoRecordAvailableException("No registrations found for page " + pageNumber + " with size " + pageSize);
	}
}
