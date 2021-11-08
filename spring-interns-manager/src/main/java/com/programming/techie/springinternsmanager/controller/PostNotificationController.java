package com.programming.techie.springinternsmanager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.programming.techie.springinternsmanager.dto.PostNotificationResponse;
import com.programming.techie.springinternsmanager.model.PostNotification;
import com.programming.techie.springinternsmanager.service.PostNotificationService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/notification/")
public class PostNotificationController {
	
	private final PostNotificationService postNotificationService;
	
	@GetMapping("/all")
	public ResponseEntity<List<PostNotification>> getAllNotifications() {
		List<PostNotification> notifications = postNotificationService.getAll();
		return new ResponseEntity<>(notifications, HttpStatus.OK);
	}
	@GetMapping("subreddit/{id}")
	public ResponseEntity<List<PostNotificationResponse>> getNotificationsBySubreddit (@PathVariable long id){
		List<PostNotificationResponse> notifications= postNotificationService.getNotificationBySubredditId(id);
		return new ResponseEntity<>(notifications, HttpStatus.OK);
		
	}
	
	@GetMapping("/{rn}")
	public ResponseEntity<List<PostNotificationResponse>> getNotificationsByRole (@PathVariable String rn){
		List<PostNotificationResponse> notifications= postNotificationService.getNotificationsByRole(rn);
		return new ResponseEntity<>(notifications, HttpStatus.OK);
	}
	
	
	
	@PostMapping
	public ResponseEntity<PostNotification> createNotification(@RequestBody PostNotification postNotification ){
		PostNotification notification = postNotificationService.save(postNotification);
		return new ResponseEntity<>(notification, HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteNotification(@PathVariable Long id ){
		postNotificationService.deleteNotificationById(id);
		return new ResponseEntity<>( HttpStatus.OK);
	}
	
	@DeleteMapping("post/{postId}")
	public ResponseEntity<?> deleteNotificationByPostId(@PathVariable Long postId ){
		postNotificationService.deleteNotificationByPostId(postId);
		return new ResponseEntity<>( HttpStatus.OK);
	}
	
	

}
