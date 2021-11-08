package com.programming.techie.springinternsmanager.controller;

import static org.springframework.http.ResponseEntity.status;  

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programming.techie.springinternsmanager.dto.CountType;
import com.programming.techie.springinternsmanager.dto.TaskDto;
import com.programming.techie.springinternsmanager.model.Task;
import com.programming.techie.springinternsmanager.model.User;
import com.programming.techie.springinternsmanager.repository.UserRepository;
import com.programming.techie.springinternsmanager.service.AuthService;
import com.programming.techie.springinternsmanager.service.TaskService;

import lombok.AllArgsConstructor;


@RestController
@AllArgsConstructor
@RequestMapping("/api/tasks/")
public class TaskController {
	
	private final TaskService taskService;
	private final AuthService authService;
	private final UserRepository userRepository;
	
	@PostMapping("/createTask")
	public ResponseEntity<Task> createTask(@RequestBody TaskDto taskDto) {
		return status(HttpStatus.CREATED).body(taskService.createTask(taskDto));
		
	}
	
	@GetMapping("/userTasks")
	public ResponseEntity<List<Task>>getTasksByCurrentUser(){	
		return status(HttpStatus.OK).body(taskService.getTasksByCurrentUser());
		
	}
	
	
	@GetMapping("/task/{id}")
	public ResponseEntity<?>getTasksById(@PathVariable Long id){	
		return status(HttpStatus.OK).body(taskService.getTaskById(id));
		
	}
	
	
	
	
	
	@PutMapping("/{id}")
	public ResponseEntity<Task> updateTask(@RequestBody TaskDto taskDto,@PathVariable Long id) {
		return status(HttpStatus.OK).body(taskService.updateTask(taskDto, id));
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteTask(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(taskService.removeTask(id));
	}
	
	@GetMapping("/vData/percentcounttype")
	public List<CountType> getPercentageGroupByTypeByUser(){
		return taskService.getPercentageGroupByType(authService.getCurrentUser());
	}
	
	@GetMapping("/vData/percentcounttype-user/{username}")
	public List<CountType> getPercentageGroupByType(@PathVariable String username){
		User user = userRepository.findByUsername(username).orElseThrow();
		return taskService.getPercentageGroupByType(user);
	}
	
	@GetMapping("/taskByUserName/{name}")
	public ResponseEntity<?>  getTasksByUserName(@PathVariable String name) {
		return ResponseEntity.status(HttpStatus.OK).body(taskService.getTaskByUsername(name));
				
	}
	

}
