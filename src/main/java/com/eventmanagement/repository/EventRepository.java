package com.eventmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eventmanagement.entity.Event;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eventmanagement.entity.Attendee;

public interface EventRepository extends JpaRepository<Event, Integer> {
	
	@Query("SELECT r.attendee FROM Registration r WHERE r.event.id = :eventId")
	List<Attendee> getAttendeesByEventId(@Param("eventId") int eventId);

	@Query("SELECT e FROM Event e WHERE e.organizer.id = :organizerId")
	List<Event> getEventsByOrganizerId(@Param("organizerId") int organizerId);

}
