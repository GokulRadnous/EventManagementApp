package com.eventmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eventmanagement.entity.Attendee;
import com.eventmanagement.entity.Registration;

public interface AttendeeRepository extends JpaRepository<Attendee, Integer> {

	Attendee findByContact(long contact);
	
	@Query("SELECT a.registrations FROM Attendee a WHERE a.id = ?1")
	List<Registration> getRegistrationsByAttendee(int attendeeId);

}