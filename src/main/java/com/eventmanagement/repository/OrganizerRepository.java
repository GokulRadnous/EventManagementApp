package com.eventmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventmanagement.entity.Organizer;

public interface OrganizerRepository extends JpaRepository<Organizer, Integer>{

}
