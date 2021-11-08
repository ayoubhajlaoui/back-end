package com.programming.techie.springinternsmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.programming.techie.springinternsmanager.model.PostNotification;
import com.programming.techie.springinternsmanager.model.Role;
import com.programming.techie.springinternsmanager.model.User;

@Repository
public interface PostNotificationRepository extends JpaRepository<PostNotification, Long> {
	void deleteById(Long id);
	void deleteByPostId(Long postId);
	List<PostNotification> findBySubredditId(Long subredditId);
	
	List<PostNotification> findByUser (User user);
	
	List<PostNotification> findByRole (Role role);

}
