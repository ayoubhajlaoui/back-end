package com.programming.techie.springinternsmanager.service;

import org.springframework.stereotype.Service;

import com.programming.techie.springinternsmanager.dto.ProgramDto;
import com.programming.techie.springinternsmanager.model.Program;
import com.programming.techie.springinternsmanager.repository.ProgramRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProgramService {
	
	
	private final ProgramRepository programRepository;
	private final AuthService authService;
	
	public Program createProgram (ProgramDto programDto) {
		
		return programRepository.save(mapToProgram(programDto));
		
	}
	
	
	
	
	public Program mapToProgram(ProgramDto programDto) {
		return Program.builder()
				.name(programDto.getName())
				.description(programDto.getDescription())
				.startDate(programDto.getStartDate())
				.finishDate(programDto.getFinishDate())
				//.user(authService.getCurrentUser())
				.build();
	}
	
	

}
