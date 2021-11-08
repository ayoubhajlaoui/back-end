package com.programming.techie.springinternsmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.programming.techie.springinternsmanager.model.Program;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
	
	public Program findByName(String name);

}
