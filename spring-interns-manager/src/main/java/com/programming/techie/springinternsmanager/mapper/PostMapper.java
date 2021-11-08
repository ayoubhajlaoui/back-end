package com.programming.techie.springinternsmanager.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.programming.techie.springinternsmanager.dto.PostRequest;
import com.programming.techie.springinternsmanager.dto.PostResponse;
import com.programming.techie.springinternsmanager.model.*;
import com.programming.techie.springinternsmanager.repository.CommentRepository;
import com.programming.techie.springinternsmanager.repository.VoteRepository;
import com.programming.techie.springinternsmanager.service.AuthService;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import static com.programming.techie.springinternsmanager.model.VoteType.DOWNVOTE;
import static com.programming.techie.springinternsmanager.model.VoteType.UPVOTE;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class PostMapper {
	
	
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthService authService;
    
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "targetUsers", source ="targetUsers" )
    @Mapping(target = "file", source = "file")
    public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user, List<User> targetUsers, FileDB file);
        
    @Mapping(target = "id", source = "postId")
    @Mapping(target = "fileName", source = "file.name")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))")
    @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
    public abstract PostResponse mapToDto(Post post);

    Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }

    boolean isPostUpVoted(Post post) {
        return checkVoteType(post, UPVOTE);
    }

    boolean isPostDownVoted(Post post) {
        return checkVoteType(post, DOWNVOTE);
    }

    private boolean checkVoteType(Post post, VoteType voteType) {
        if (authService.isLoggedIn()) {
            Optional<Vote> voteForPostByUser =
                    voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
                            authService.getCurrentUser());
            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
                    .isPresent();
        }
        return false;
    }

}