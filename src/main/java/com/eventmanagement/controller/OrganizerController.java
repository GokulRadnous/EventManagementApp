package com.eventmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventmanagement.dto.ResponseStructure;
import com.eventmanagement.entity.Organizer;
import com.eventmanagement.service.OrganizerService;

@RestController
@RequestMapping("/ema/organizer")
public class OrganizerController {
	@Autowired
	private OrganizerService organizerService;

	@PostMapping
	public ResponseEntity<ResponseStructure<Organizer>> saveOrganizer(@RequestBody Organizer organizer) {
		return organizerService.saveOrganizer(organizer);
	}

	@GetMapping
	public ResponseEntity<ResponseStructure<List<Organizer>>> getAllOrganizer() {
		return organizerService.getAllOrganizer();
	}

	@GetMapping("{id}")
	public ResponseEntity<ResponseStructure<Organizer>> getOrganizerById(@PathVariable int id) {
		return organizerService.findById(id);
	}

	@PutMapping
	public ResponseEntity<ResponseStructure<Organizer>> updateOrganizer(@RequestBody Organizer organizer) {
		return organizerService.updateByOrganizer(organizer);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteVenue(@PathVariable int id) {
		return organizerService.deleteOrganizer(id);
	}

	@GetMapping("/pagination/{pagenumber}/{pagesize}/{feild}")
	public ResponseEntity<ResponseStructure<List<Organizer>>> getOrganizerByPaginationAndSorting(
			@PathVariable int pagenumber, @PathVariable int pagesize, @PathVariable String feild) {
		return organizerService.getOrganizerByPaginationAndSorting(pagenumber, pagesize, feild);

	}
}
