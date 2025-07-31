package com.eventmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.eventmanagement.dto.ResponseStructure;
import com.eventmanagement.entity.Attendee;
import com.eventmanagement.entity.Registration;
import com.eventmanagement.service.AttendeeService;

@RestController
@RequestMapping("/ema/attendee")
public class AttendeeController {

    @Autowired
    private AttendeeService attendeeService;

    @PostMapping
    public ResponseEntity<ResponseStructure<Attendee>> saveAttendee(@RequestBody Attendee attendee) {
        return attendeeService.saveAttendee(attendee);
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<Attendee>>> getAllAttendees() {
        return attendeeService.getAllAttendees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<Attendee>> getAttendeeById(@PathVariable int id) {
        return attendeeService.findAttendeeById(id);
    }

    @PutMapping
    public ResponseEntity<ResponseStructure<Attendee>> updateAttendee(@RequestBody Attendee attendee) {
        return attendeeService.updateAttendee(attendee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> deleteAttendee(@PathVariable int id) {
        return attendeeService.deleteAttendee(id);
    }

    @GetMapping("/contact/{contact}")
    public ResponseEntity<ResponseStructure<Attendee>> getAttendeeByContact(@PathVariable long contact) {
        return attendeeService.getAttendeeByContact(contact);
    }

    @GetMapping("/registrations/{id}")
    public ResponseEntity<ResponseStructure<List<Registration>>> getRegistrationsByAttendee(@PathVariable int id) {
        return attendeeService.getRegistrationsByAttendee(id);
    }

    @GetMapping("/pagination/{pagenumber}/{pagesize}/{field}")
    public ResponseEntity<ResponseStructure<List<Attendee>>> getAttendeesByPaginationAndSorting(
            @PathVariable int pagenumber, @PathVariable int pagesize, @PathVariable String field) {
        return attendeeService.getAttendeeByPaginationAndSorting(pagenumber, pagesize, field);
    }
}
