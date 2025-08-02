package com.eventmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eventmanagement.entity.Registration;

public interface RegistrationRepository extends JpaRepository<Registration, Integer> {

	@Query("SELECT r FROM Registration r WHERE r.event.id = ?1")
	List<Registration> getRegistrationByEventId(Integer eventId);

	@Query("SELECT r FROM Registration r WHERE r.attendee.id = ?1")
	List<Registration> getRegistrationByAttendeeId(Integer attendeeId);

}
