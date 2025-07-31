package com.eventmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.eventmanagement.dto.ResponseStructure;
import com.eventmanagement.entity.Registration;
import com.eventmanagement.service.RegistrationService;

@RestController
@RequestMapping("/ema/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<ResponseStructure<Registration>> createRegistration(@RequestBody Registration registration) {
        return registrationService.createRegistration(registration);
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<Registration>>> getAllRegistration() {
        return registrationService.getAllRegistration();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<Registration>> getRegistrationById(@PathVariable int id) {
        return registrationService.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> cancelRegistration(@PathVariable int id) {
        return registrationService.cancelRegistration(id);
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<ResponseStructure<List<Registration>>> getRegistrationByEventId(@PathVariable int id) {
        return registrationService.getRegistrationsByEventId(id);
    }

    @GetMapping("/attendee/{id}")
    public ResponseEntity<ResponseStructure<List<Registration>>> getRegistrationByAttendee(@PathVariable int id) {
        return registrationService.getRegistrationByAttendee(id);
    }
}
