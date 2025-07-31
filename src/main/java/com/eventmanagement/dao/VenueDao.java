package com.eventmanagement.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.eventmanagement.entity.Event;
import com.eventmanagement.entity.Venue;
import com.eventmanagement.repository.VenueRepository;

@Repository
public class VenueDao {
	@Autowired
	private VenueRepository venueRepository;

	public Venue saveVenue(Venue venue) {
		return venueRepository.save(venue);
	}

	public List<Venue> getAllVenue() {
		return venueRepository.findAll();
	}

	public Optional<Venue> findById(int id) {
		return venueRepository.findById(id);
	}

	public Optional<Venue> updateVenue(Venue venue) {
		Optional<Venue> existingVenue = venueRepository.findById(venue.getId());

		if (existingVenue.isPresent()) {
			Venue updatedVenue = existingVenue.get();
			updatedVenue.setName(venue.getName());
			updatedVenue.setLocation(venue.getLocation());
			updatedVenue.setCapacity(venue.getCapacity());
			return Optional.of(venueRepository.save(updatedVenue));
		}
		return Optional.empty();
	}

	public void deleteVenue(Venue venue) {
		venueRepository.delete(venue);
	}

	public List<Event> getEventsByVenueId(int venueId) {
		return venueRepository.getEventsByVenueId(venueId);
	}

	public List<Venue> getVenueByLocation(String location) {
		return venueRepository.findByLocation(location);
	}

	public Page<Venue> getVenueByPaginationAndSorting(int pageNumber, int pageSize, String field) {
		return venueRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(field).ascending()));
	}
}
