package com.programming.techie.springinternsmanager.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.programming.techie.springinternsmanager.dto.CommentsDto;
import com.programming.techie.springinternsmanager.model.Comment;
import com.programming.techie.springinternsmanager.model.Post;
import com.programming.techie.springinternsmanager.model.User;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentsDto.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    public abstract Comment map(CommentsDto commentsDto, Post post, User user);
    
    @InheritInverseConfiguration
    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
    
    
    public abstract CommentsDto mapToDto(Comment comment);
     
}