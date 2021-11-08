package com.programming.techie.springinternsmanager.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.programming.techie.springinternsmanager.dto.CommentsDto;
import com.programming.techie.springinternsmanager.exceptions.PostNotFoundException;
import com.programming.techie.springinternsmanager.mapper.CommentMapper;
import com.programming.techie.springinternsmanager.model.Comment;
import com.programming.techie.springinternsmanager.model.NotificationEmail;
import com.programming.techie.springinternsmanager.model.Post;
import com.programming.techie.springinternsmanager.model.User;
import com.programming.techie.springinternsmanager.repository.CommentRepository;
import com.programming.techie.springinternsmanager.repository.PostRepository;
import com.programming.techie.springinternsmanager.repository.UserRepository;

import java.util.List;

import javax.persistence.EntityManager;

import static java.util.stream.Collectors.toList;

import java.util.Comparator;

@Service
@AllArgsConstructor
public class CommentService {
    private static final String POST_URL = "";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;
    private final EntityManager entityManager; 

    public void save(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
        Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

        String message = mailContentBuilder.build(authService.getCurrentUser() + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message);
    }

    private void sendCommentNotification(String message) {
        mailService.sendMail(new NotificationEmail(authService.getCurrentUser().getUsername() + " Commented on your post", authService.getCurrentUser().getEmail(), message));
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream().sorted(Comparator.comparing(Comment::getCreatedDate).reversed())
                .map(commentMapper::mapToDto).collect(toList());
    }

    public List<CommentsDto> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }
    public void removePost (Long postId) {
    	Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));;
    	List<Comment> commentList= commentRepository.findByPost(post);
    	for(Comment comment :commentList) {
    		post=comment.getPost();
    		comment.setPost(null);
    		entityManager.remove(post);
    	}
    	
    }
}
