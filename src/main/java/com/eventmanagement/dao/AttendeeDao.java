package com.eventmanagement.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.eventmanagement.entity.Attendee;
import com.eventmanagement.entity.Registration;
import com.eventmanagement.repository.AttendeeRepository;

@Repository
public class AttendeeDao {

	@Autowired
	private AttendeeRepository attendeeRepository;

	public Attendee saveAttendee(Attendee Attendee) {
		return attendeeRepository.save(Attendee);
	}

	public List<Attendee> getAllAttendee() {
		return attendeeRepository.findAll();
	}

	public Optional<Attendee> findById(int id) {
		return attendeeRepository.findById(id);
	}

	public Optional<Attendee> updateAttendee(Attendee attendee) {
		Optional<Attendee> existing = attendeeRepository.findById(attendee.getId());

		if (existing.isPresent()) {
			Attendee existingAttendee = existing.get();

			existingAttendee.setName(attendee.getName());
			existingAttendee.setEmail(attendee.getEmail());
			existingAttendee.setContact(attendee.getContact());
			return Optional.of(attendeeRepository.save(existingAttendee));
		}

		return Optional.empty();
	}

	public void deleteAttendee(Attendee attendee) {
		attendeeRepository.delete(attendee);
	}

	public List<Registration> getRegistartionByAttendee(int id) {
		return attendeeRepository.getRegistrationsByAttendee(id);
	}

	public Attendee getAttendeeByConduct(Long contact) {
		return attendeeRepository.findByContact(contact);
	}

	public Page<Attendee> getAttendeeByPaginationAndSorting(int pageNumber, int pageSize, String field) {
		return attendeeRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(field).ascending()));
	}

}
