package com.programming.techie.springinternsmanager.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.Instant;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostNotification {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	private String message;
	private boolean vue ;
	private Long postId;
	private Long subredditId;
	private Instant createdDate;
	
	@ManyToOne
	private Role role;
	
	@ManyToOne
	private User user; 
	
	
	//private PostNotification postNotification;  
//	long userId;

}
