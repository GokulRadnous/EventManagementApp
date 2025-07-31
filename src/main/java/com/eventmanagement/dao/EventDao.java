package com.eventmanagement.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.eventmanagement.entity.Attendee;
import com.eventmanagement.entity.Event;
import com.eventmanagement.repository.EventRepository;
@Repository
public class EventDao {
	@Autowired
	EventRepository eventRepository;

	public Event save(Event event) {
		return eventRepository.save(event);
	}

	public List<Event> getAllEvent() {
		return eventRepository.findAll();
	}

	public Optional<Event> findById(int id) {
		return eventRepository.findById(id);
	}

	public Optional<Event> updateEvent(Event event) {
		Optional<Event> opt = eventRepository.findById(event.getId());
		return opt;
	}

	public void deleteEvent(Event event) {
		eventRepository.delete(event);
	}

	public List<Attendee> getAttendeeByEventId(int EventId) {
		return eventRepository.getAttendeesByEventId(EventId);
	}

	public List<Event> getEventsByOrganizerId(int organizerId) {
		return eventRepository.getEventsByOrganizerId(organizerId);
	}

	public Page<Event> getEventByPaginationAndSorting(int pageNumber, int pageSize, String field) {
		return eventRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(field).ascending()));
	}
}
