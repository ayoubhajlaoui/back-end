package com.programming.techie.springinternsmanager.mapper;


import org.apache.catalina.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.programming.techie.springinternsmanager.dto.SubredditDto;
import com.programming.techie.springinternsmanager.model.Post;
import com.programming.techie.springinternsmanager.model.Subreddit;
import com.programming.techie.springinternsmanager.service.AuthService;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class SubredditMapper {
	@Autowired
	public  AuthService authService; 
		
	 @Mapping(target = "posts", ignore = true)
	 @Mapping(target = "user", expression = "java(authService.getCurrentUser())")
	 @Mapping(target = "createdDate",  expression = "java(java.time.Instant.now())" )
	 public abstract  Subreddit mapDtoToSubreddit(SubredditDto subredditDto);
		
	
	@InheritInverseConfiguration
    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    

    public Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }
    //public String mapUser(com.programming.techie.springinternsmanager.model.User user) {
    //	return user.getUsername();
    //}
	public abstract SubredditDto mapSubredditToDto(Subreddit subreddit);
    
    
   
}
