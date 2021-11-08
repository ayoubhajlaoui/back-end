package com.programming.techie.springinternsmanager.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programming.techie.springinternsmanager.dto.CountType;
import com.programming.techie.springinternsmanager.dto.TaskDto;
import com.programming.techie.springinternsmanager.model.Program;
import com.programming.techie.springinternsmanager.model.Task;
import com.programming.techie.springinternsmanager.model.User;
import com.programming.techie.springinternsmanager.repository.ProgramRepository;
import com.programming.techie.springinternsmanager.repository.TaskRepository;
import com.programming.techie.springinternsmanager.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service 
@AllArgsConstructor
@Slf4j
@Transactional
public class TaskService {
	
	private final TaskRepository taskRepository;
	private final ProgramRepository programRepository;
	private final AuthService authService;
	private final UserRepository userRepository;
	
	public Task createTask(TaskDto taskDto) {
		return taskRepository.save(mapToTask(taskDto));
	}
	
	private Task mapToTask(TaskDto taskDto) {
		
		return Task.builder()
				.name(taskDto.getName())
				.description(taskDto.getDescription())
				//.program(programRepository.findByName(taskDto.getProgramName()))
				.user(authService.getCurrentUser())
				.type(taskDto.getType())
				.dueDate(taskDto.getDueDate())
				.build();
	}
	
	private TaskDto mapToTaskDto(Task task) {
		return TaskDto.builder()
				.name(task.getName())
				.description(task.getDescription())
				//.programName(task.getProgram().getName())
				.type(task.getType())
				.dueDate(task.getDueDate())
				.username(task.getUser().getUsername())
				.build();
	}
	
	
	
	public List<Task> getTasksByCurrentUser(){
		return taskRepository.findAllByUser(authService.getCurrentUser());
	}
	
	public List<Task> getTasksByUser(User user){
		return taskRepository.findAllByUser(user);
	}
	
	
	
	public Task updateTask (TaskDto taskDto, Long id) {
		
		Task task=new Task();
		if(existById(id)) {
			task=getTaskById(id).orElseThrow(()->new EntityNotFoundException("Requested Task not found"));
			task.setName(taskDto.getName());
			task.setDueDate(taskDto.getDueDate());
			task.setType(taskDto.getType());
			task.setDescription(taskDto.getDescription());
			
		}
		return taskRepository.save(task);
			
	}
	
	public HashMap<String, String> delete(Long id) {
		
		HashMap<String, String>message= new HashMap<>();
		if(existById(id)) {
			taskRepository.deleteById(id);
			message.put("message", id + " task removed");
		}
//		else {
//			message.put("message", id + " task not found or matched");
//		}
		return message;
		
	}
	
	public List<CountType> getPercentageGroupByType( User user) {
		return taskRepository.getPercentageGroupByTypeByUser(user);
		
	}
	
	public boolean existById(Long id) {
		return taskRepository.existsById(id);
	}
	
	@Transactional(readOnly = true)
	public Optional<Task> getTaskById(Long id) {
		return taskRepository.findById(id);
	}
	
	@Transactional(readOnly = true)
	public List<Task> getTaskByUsername(String name) {
		User user = userRepository.findByUsername(name).orElseThrow(()->new EntityNotFoundException("user not found"));
		List<Task> tasks =  taskRepository.findAllByUser(user);
		return tasks;
	}
	 public HashMap<String, String> removeTask(Long taskId) {
		 Task task = taskRepository.findById(taskId).orElseThrow(()->new EntityNotFoundException("task not found"));
		 task.setUser(null);
		 return delete(taskId);
	 }
	

}
