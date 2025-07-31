package com.eventmanagement.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.eventmanagement.entity.Organizer;
import com.eventmanagement.repository.OrganizerRepository;

@Repository
public class OrganizerDao {

	@Autowired
	private OrganizerRepository organizerRepository;

	public Organizer saveOrganizer(Organizer organizer) {
		return organizerRepository.save(organizer);
	}

	public List<Organizer> getAllOrganizer() {
		return organizerRepository.findAll();
	}

	public Optional<Organizer> findById(int id) {
		return organizerRepository.findById(id);
	}

	public Optional<Organizer> updateOrganizer(Organizer organizer) {
		Optional<Organizer> existing = organizerRepository.findById(organizer.getId());

		if (existing.isPresent()) {
			Organizer existingOrganizer = existing.get();

			existingOrganizer.setName(organizer.getName());
			existingOrganizer.setEmail(organizer.getEmail());
			existingOrganizer.setOrganization(organizer.getOrganization());

			return Optional.of(organizerRepository.save(existingOrganizer));
		}

		return Optional.empty();
	}

	public void deleteOrganizer(Organizer organizer) {
		organizerRepository.delete(organizer);
	}

	public Page<Organizer> getOrganizerByPaginationAndSorting(int pageNumber, int pageSize, String field) {
		return organizerRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(field).ascending()));
	}
}
