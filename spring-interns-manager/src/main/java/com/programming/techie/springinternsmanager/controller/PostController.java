package com.programming.techie.springinternsmanager.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.programming.techie.springinternsmanager.dto.PostRequest;
import com.programming.techie.springinternsmanager.dto.PostResponse;
import com.programming.techie.springinternsmanager.model.Post;
import com.programming.techie.springinternsmanager.model.PostNotification;
import com.programming.techie.springinternsmanager.service.AuthService;
import com.programming.techie.springinternsmanager.service.PostNotificationService;
import com.programming.techie.springinternsmanager.service.PostService;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/posts/")
@AllArgsConstructor
public class PostController {

	
    private final PostService postService;
    private final  PostNotificationService postNotificationService;
    private final AuthService authService; 

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) {
        Post post = postService.save(postRequest);
        
        
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return status(HttpStatus.OK).body(postService.getPost(id));
    }

    @GetMapping("by-subreddit/{id}")
    public ResponseEntity<List<PostResponse>> getPostsBySubreddit(@PathVariable Long id) {
        return status(HttpStatus.OK).body(postService.getPostsBySubreddit(id));
    }

    @GetMapping("by-user/{name}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String name) {
        return status(HttpStatus.OK).body(postService.getPostsByUsername(name));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id){
    	postService.removePost(id);
    	return new ResponseEntity<>( HttpStatus.OK);
    }
        
    @GetMapping("admin-posts/{userId}")
    public ResponseEntity<List<PostResponse>> getAdminPosts(@PathVariable Long userId){
    	return status(HttpStatus.OK).body(postService.getPostsFromAdminAndFortargetUser(userId));
    }
}
