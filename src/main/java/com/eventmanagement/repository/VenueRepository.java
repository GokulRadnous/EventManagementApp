package com.eventmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eventmanagement.entity.Event;
import com.eventmanagement.entity.Venue;

public interface VenueRepository extends JpaRepository<Venue, Integer> {

	@Query("SELECT e FROM Event e WHERE e.venue.id = :venueId")
	List<Event> getEventsByVenueId(@Param("venueId") Integer venueId);

	List<Venue> findByLocation(String location);

}
