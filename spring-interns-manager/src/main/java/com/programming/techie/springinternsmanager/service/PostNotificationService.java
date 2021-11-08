package com.programming.techie.springinternsmanager.service;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.programming.techie.springinternsmanager.dto.PostNotificationResponse;
import com.programming.techie.springinternsmanager.exceptions.SpringRedditException;
import com.programming.techie.springinternsmanager.model.PostNotification;
import com.programming.techie.springinternsmanager.model.Role;
import com.programming.techie.springinternsmanager.model.RoleName;
import com.programming.techie.springinternsmanager.model.User;
import com.programming.techie.springinternsmanager.repository.PostNotificationRepository;
import com.programming.techie.springinternsmanager.repository.RoleRepository;
import com.programming.techie.springinternsmanager.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostNotificationService {
	
	private final PostNotificationRepository postNotificationRepository;
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;

	public List<PostNotification> getAll() {
		
		return  postNotificationRepository.findAll();
		
		
	}

	public PostNotification  save(PostNotification postNotification) {
		return  postNotificationRepository.save(postNotification);
		
	}
	
	
	
	public void  deleteNotificationById( Long id) {
		  postNotificationRepository.deleteById(id);
		
	}
	
	public void  deleteNotificationByPostId( Long postId) {
		  postNotificationRepository.deleteByPostId(postId);
		
	}
	
	public List<PostNotificationResponse> getNotificationBySubredditId(Long subredditId){
		
	
		
		return postNotificationRepository.findBySubredditId(subredditId)
				.stream()
				.map(PostNotificationService::mapToDto).collect(toList());
	}
	
	public List<PostNotificationResponse> getNotificationsByRole (String rn){
		RoleName roleName = RoleName.valueOf(rn);
    	Role role = roleRepository.findByName(roleName).orElseThrow(() -> new SpringRedditException(""));
    	List<Role> roles = new ArrayList<Role>();
    	//roles.add(role);
    	//User user = userRepository.findByRoles(roles) ;
    	return postNotificationRepository.findByRole(role)
    			.stream()
				.map(PostNotificationService::mapToDto).collect(toList());
	}
	
	 
	private static PostNotificationResponse mapToDto (PostNotification postNotification ) {
		
		return PostNotificationResponse.builder()
				.id(postNotification.getId())
				.message(postNotification.getMessage())
				.postId(postNotification.getPostId())
				.subredditId(postNotification.getSubredditId())
				.createdDate(postNotification.getCreatedDate())
				.imageUrl(postNotification.getUser().getImageUrl())
				.build();
	}


}
