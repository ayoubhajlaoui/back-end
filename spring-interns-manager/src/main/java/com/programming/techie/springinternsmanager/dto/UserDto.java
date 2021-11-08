package com.programming.techie.springinternsmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
	private Long userId;
	private String username;
	private String email;
    private String imageUrl;
    private String projectTitle;
    private String phone;
    private String role;
    private boolean enabled;

}
