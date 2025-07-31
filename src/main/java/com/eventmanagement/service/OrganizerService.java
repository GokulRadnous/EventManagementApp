package com.eventmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eventmanagement.dao.OrganizerDao;
import com.eventmanagement.dto.ResponseStructure;
import com.eventmanagement.entity.Organizer;
import com.eventmanagement.exception.IdNotFoundException;
import com.eventmanagement.exception.NoRecordAvailableException;

@Service
public class OrganizerService {

	@Autowired
	private OrganizerDao organizerDao;

	public ResponseEntity<ResponseStructure<Organizer>> saveOrganizer(Organizer organizer) {
		Organizer savedOrganizer = organizerDao.saveOrganizer(organizer);

		ResponseStructure<Organizer> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Organizer saved successfully");
		response.setData(savedOrganizer);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<List<Organizer>>> getAllOrganizer() {
		List<Organizer> organizers = organizerDao.getAllOrganizer();

		if (!organizers.isEmpty()) {
			ResponseStructure<List<Organizer>> response = new ResponseStructure<>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("All organizers retrieved successfully");
			response.setData(organizers);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		throw new NoRecordAvailableException("Database is empty. No organizers found.");
	}

	public ResponseEntity<ResponseStructure<Organizer>> findById(int id) {
		Optional<Organizer> organizer = organizerDao.findById(id);

		if (organizer.isPresent()) {
			ResponseStructure<Organizer> response = new ResponseStructure<>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Organizer found successfully");
			response.setData(organizer.get());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		throw new NoRecordAvailableException("Organizer with ID " + id + " not found.");
	}

	public ResponseEntity<ResponseStructure<Organizer>> updateByOrganizer(Organizer organizer) {
		Optional<Organizer> updated = organizerDao.updateOrganizer(organizer);

		if (updated.isPresent()) {
			ResponseStructure<Organizer> response = new ResponseStructure<>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Organizer updated successfully");
			response.setData(updated.get());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		throw new NoRecordAvailableException("Organizer with ID " + organizer.getId() + " not found.");
	}

	public ResponseEntity<ResponseStructure<String>> deleteOrganizer(int id) {
		Optional<Organizer> opt = organizerDao.findById(id);

		if (opt.isPresent()) {
			organizerDao.deleteOrganizer(opt.get());

			ResponseStructure<String> response = new ResponseStructure<>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Organizer record deleted successfully");
			response.setData("Success");

			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		throw new IdNotFoundException("Organizer ID " + id + " not available in the database");
	}
	public ResponseEntity<ResponseStructure<List<Organizer>>> getOrganizerByPaginationAndSorting(int pageNumber, int pageSize, String field) {
		Page<Organizer> organizerPage = organizerDao.getOrganizerByPaginationAndSorting(pageNumber, pageSize, field);

		if (!organizerPage.isEmpty()) {
			ResponseStructure<List<Organizer>> response = new ResponseStructure<>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Retrieved organizers sorted by field: " + field);
			response.setData(organizerPage.getContent());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		throw new NoRecordAvailableException("No organizers found for page " + pageNumber + " with size " + pageSize);
	}


}
