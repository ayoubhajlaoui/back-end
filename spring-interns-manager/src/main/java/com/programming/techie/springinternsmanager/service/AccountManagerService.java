package com.programming.techie.springinternsmanager.service;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.springframework.core.task.TaskRejectedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programming.techie.springinternsmanager.dto.UserDto;
import com.programming.techie.springinternsmanager.exceptions.UserNotFoundException;
import com.programming.techie.springinternsmanager.model.Comment;
import com.programming.techie.springinternsmanager.model.FileDB;
import com.programming.techie.springinternsmanager.model.Post;
import com.programming.techie.springinternsmanager.model.PostNotification;
import com.programming.techie.springinternsmanager.model.Subreddit;
import com.programming.techie.springinternsmanager.model.Task;
import com.programming.techie.springinternsmanager.model.User;
import com.programming.techie.springinternsmanager.model.VerificationToken;
import com.programming.techie.springinternsmanager.model.Vote;
import com.programming.techie.springinternsmanager.repository.CommentRepository;
import com.programming.techie.springinternsmanager.repository.FileDBRepository;
import com.programming.techie.springinternsmanager.repository.PostNotificationRepository;
import com.programming.techie.springinternsmanager.repository.PostRepository;
import com.programming.techie.springinternsmanager.repository.SubredditRepository;
import com.programming.techie.springinternsmanager.repository.TaskRepository;
import com.programming.techie.springinternsmanager.repository.UserRepository;
import com.programming.techie.springinternsmanager.repository.VerificationTokenRepository;
import com.programming.techie.springinternsmanager.repository.VoteRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service 
@AllArgsConstructor
@Transactional
public class AccountManagerService {
	
	private final UserRepository userRepository;
	private final EntityManager entityManager;
	private final TaskRepository taskRepository;
	private final PostRepository postRepository;
	private final VerificationTokenRepository verificationTokenRepository;
	private final CommentRepository commentRepository;
	private final VoteRepository voteRepository;
	private final FileDBRepository fileDBRepository;
	private final PostNotificationRepository postNotificationRepository;
	private final SubredditRepository subredditRepository;
	private final AuthService authService;
	
	public UserDto mapToDto (User user) {
		return UserDto.builder()
				.username(user.getUsername())
				.email(user.getEmail())
				.role(user.getRoles().get(0).getName().toString())
				.imageUrl(user.getImageUrl())
				.projectTitle(user.getProjectTitle())
				.phone(user.getPhone())
				.enabled(user.isEnabled())
				.build();
	}
	
	 public User addUser(User user) {
	        return userRepository.save(user);
	    }

	    public List<User> findAllUsers() {
	    	return userRepository.findAll();
	    			
	    }
	    
	    @Transactional
	    public User updateUser(User user) {
	    	//user.setPassword(authService.getCurrentUser().getPassword());
	    	 User userFromDb = authService.getCurrentUser();
	    	 userFromDb.setProjectTitle(user.getProjectTitle());
	    	 userFromDb.setEmail(user.getEmail());
	    	 userFromDb.setImageUrl(user.getImageUrl());
	    	 userFromDb.setPhone(user.getPhone());
	    	 
	        return userRepository.saveAndFlush(userFromDb);
	    }

	    public User findUserById(String username) {
	        return userRepository.findByUsername(username)
	                .orElseThrow(() -> new UserNotFoundException("User by id " + username + " was not found"));
	    }

	    public void deleteUser(Long id){
	        userRepository.deleteByUserId(id);
	    }
	    
	    public void removeUser (Long userId) {
	    	User user= userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User by id " + userId + " was not found"));
	    	List<Task> tasks = taskRepository.findAllByUser(user);
	    	for(Task task:tasks) {
	    		user = task.getUser();
	    		task.setUser(null);
	    		entityManager.remove(user);
	    	}
	    	List<Post> posts = postRepository.findByUser(user);
	    	for(Post post:posts) {
	    		user = post.getUser();
	    		post.setUser(null);
	    		entityManager.remove(user);
	    	}
	    	
	    	VerificationToken token= verificationTokenRepository.findByUser(user);
	    	user = token.getUser();
	    	token.setUser(null);
	    	entityManager.remove(user);
	    	
	    	List<Comment> comments = commentRepository.findAllByUser(user);
	    	for(Comment comment:comments) {
	    		user = comment.getUser();
	    		comment.setUser(null);
	    		entityManager.remove(user);
	    	}
	    	
	    	List<Vote> votes = voteRepository.findAllByUser(user);
	    	for(Vote vote:votes) {
	    		user = vote.getUser();
	    		vote.setUser(null);
	    		entityManager.remove(user);
	    	}
	    	
	    	List<FileDB> files = fileDBRepository.findAllByUser(user);
	    	for(FileDB file:files){
	    		user = file.getUser();
	    		file.setUser(null);
	    		entityManager.remove(user);
	    	}  
	    	
	    	List<PostNotification> notifications = postNotificationRepository.findByUser(user);
	    	for(PostNotification notification:notifications){
	    		user = notification.getUser();
	    		notification.setUser(null);
	    		entityManager.remove(user);
	    	}  
	    	
	    	List<Subreddit> subreddits = subredditRepository.findAllByUser(user);
	    	for(Subreddit subreddit:subreddits){
	    		user = subreddit.getUser();
	    		subreddit.setUser(null);
	    		entityManager.remove(user);
	    	}  
	    }
	    
	}



