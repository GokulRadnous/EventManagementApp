package com.eventmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eventmanagement.dao.AttendeeDao;
import com.eventmanagement.dto.ResponseStructure;
import com.eventmanagement.entity.Attendee;
import com.eventmanagement.entity.Registration;
import com.eventmanagement.exception.IdNotFoundException;
import com.eventmanagement.exception.NoRecordAvailableException;

@Service
public class AttendeeService {

	@Autowired
	private AttendeeDao attendeeDao;

	public ResponseEntity<ResponseStructure<Attendee>> saveAttendee(Attendee attendee) {
		Attendee saved = attendeeDao.saveAttendee(attendee);

		ResponseStructure<Attendee> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Attendee saved successfully");
		response.setData(saved);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<List<Attendee>>> getAllAttendees() {
		List<Attendee> attendees = attendeeDao.getAllAttendee();

		if (attendees.isEmpty()) {
			throw new NoRecordAvailableException("No attendees found in the database.");
		}

		ResponseStructure<List<Attendee>> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("All attendees retrieved successfully");
		response.setData(attendees);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<Attendee>> findAttendeeById(int id) {
		Optional<Attendee> attendee = attendeeDao.findById(id);

		if (attendee.isPresent()) {
			ResponseStructure<Attendee> response = new ResponseStructure<>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Attendee found successfully");
			response.setData(attendee.get());

			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		throw new NoRecordAvailableException("Attendee with ID " + id + " not found.");
	}

	public ResponseEntity<ResponseStructure<Attendee>> updateAttendee(Attendee attendee) {
		Optional<Attendee> updated = attendeeDao.updateAttendee(attendee);

		if (updated.isPresent()) {
			ResponseStructure<Attendee> response = new ResponseStructure<>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Attendee updated successfully");
			response.setData(updated.get());

			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		throw new NoRecordAvailableException("Attendee with ID " + attendee.getId() + " not found.");
	}

	public ResponseEntity<ResponseStructure<String>> deleteAttendee(int id) {
		Optional<Attendee> attendee = attendeeDao.findById(id);

		if (attendee.isPresent()) {
			attendeeDao.deleteAttendee(attendee.get());

			ResponseStructure<String> response = new ResponseStructure<>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Attendee deleted successfully");
			response.setData("Success");

			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		throw new IdNotFoundException("Attendee ID " + id + " not found.");
	}

	public ResponseEntity<ResponseStructure<List<Registration>>> getRegistrationsByAttendee(int attendeeId) {
		List<Registration> registrations = attendeeDao.getRegistartionByAttendee(attendeeId);

		if (registrations == null || registrations.isEmpty()) {
			throw new NoRecordAvailableException("No registrations found for attendee ID " + attendeeId);
		}

		ResponseStructure<List<Registration>> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Registrations fetched successfully");
		response.setData(registrations);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<Attendee>> getAttendeeByContact(long contact) {
		Attendee attendee = attendeeDao.getAttendeeByConduct(contact);

		if (attendee == null) {
			throw new NoRecordAvailableException("No attendee found with contact: " + contact);
		}

		ResponseStructure<Attendee> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Attendee retrieved successfully");
		response.setData(attendee);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<List<Attendee>>> getAttendeeByPaginationAndSorting(int pageNumber,
			int pageSize, String field) {
		Page<Attendee> attendeePage = attendeeDao.getAttendeeByPaginationAndSorting(pageNumber, pageSize, field);

		if (!attendeePage.isEmpty()) {
			ResponseStructure<List<Attendee>> response = new ResponseStructure<>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Retrieved attendees sorted by field: " + field);
			response.setData(attendeePage.getContent());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		throw new NoRecordAvailableException("No attendees found for page " + pageNumber + " with size " + pageSize);
	}

}