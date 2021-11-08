package com.programming.techie.springinternsmanager.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostNotificationResponse {
	
	private Long id;
	private String message;
	private boolean vue ;
	private Long postId;
	private Long subredditId;
	private Instant createdDate;
	private String imageUrl;

}
