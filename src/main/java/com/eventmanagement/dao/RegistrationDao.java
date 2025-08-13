package com.eventmanagement.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.eventmanagement.entity.Registration;
import com.eventmanagement.entity.Venue;
import com.eventmanagement.repository.RegistrationRepository;

@Repository
public class RegistrationDao {

	@Autowired
	private RegistrationRepository registrationRepository;

	public Registration saveRegistration(Registration registration) {
		return registrationRepository.save(registration);
	}

	public List<Registration> getAllRegistrations() {
		return registrationRepository.findAll();
	}

	public Optional<Registration> findById(int id) {
		return registrationRepository.findById(id);
	}

	public void deleteRegistration(Registration registration) {
		registrationRepository.delete(registration);
	}

	public List<Registration> getRegistrationByEventId(int id) {
		return registrationRepository.getRegistrationByEventId(id);
	}

	public List<Registration> getRegistrationByAttendee(int id) {
		return registrationRepository.getRegistrationByAttendeeId(id);
	}
	public Page<Registration> getRegistrationByPaginationAndSorting(int pageNumber, int pageSize, String field) {
		return registrationRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(field).ascending()));
	}
}
