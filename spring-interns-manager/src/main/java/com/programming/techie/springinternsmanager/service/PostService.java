package com.programming.techie.springinternsmanager.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programming.techie.springinternsmanager.dto.PostRequest;
import com.programming.techie.springinternsmanager.dto.PostResponse;
import com.programming.techie.springinternsmanager.exceptions.PostNotFoundException;
import com.programming.techie.springinternsmanager.exceptions.SpringRedditException;
import com.programming.techie.springinternsmanager.exceptions.SubredditNotFoundException;
import com.programming.techie.springinternsmanager.exceptions.UserNotFoundException;
import com.programming.techie.springinternsmanager.mapper.PostMapper;
import com.programming.techie.springinternsmanager.model.Comment;
import com.programming.techie.springinternsmanager.model.FileDB;
import com.programming.techie.springinternsmanager.model.Post;
import com.programming.techie.springinternsmanager.model.PostNotification;
import com.programming.techie.springinternsmanager.model.Role;
import com.programming.techie.springinternsmanager.model.RoleName;
import com.programming.techie.springinternsmanager.model.Subreddit;
import com.programming.techie.springinternsmanager.model.User;
import com.programming.techie.springinternsmanager.model.Vote;
import com.programming.techie.springinternsmanager.repository.CommentRepository;
import com.programming.techie.springinternsmanager.repository.FileDBRepository;
import com.programming.techie.springinternsmanager.repository.PostRepository;
import com.programming.techie.springinternsmanager.repository.RoleRepository;
import com.programming.techie.springinternsmanager.repository.SubredditRepository;
import com.programming.techie.springinternsmanager.repository.UserRepository;
import com.programming.techie.springinternsmanager.repository.VoteRepository;

import java.util.List;

import javax.persistence.EntityManager;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Comparator;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;
    private final FileDBRepository fileDBRepository;
    private final CommentRepository commentRepository;
    private final EntityManager entityManager;
    private final RoleRepository roleRepository;
    private final PostNotificationService postNotificationService;
    private final VoteRepository voteRepository;

    public Post save(PostRequest postRequest) {
    	
    	System.out.println(postRequest);
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
        
        System.out.println("target name issssss " +postRequest.getTargetNames());
        List<User> targetUsers =new ArrayList<>();
//        for(Long id :postRequest.getTargetUserIds()) {
//        	User user= userRepository.findById(id).orElseThrow(()->new UserNotFoundException("user with id "+id+" not found" ));
//        	targetUsers.add(user);
//        }
        User user= userRepository.findByUsername(postRequest.getTargetNames()).orElseThrow(()->new UserNotFoundException("user with id "+postRequest.getTargetNames()+" not found" ));
    	targetUsers.add(user);
    	
    	
        
        
        //System.out.println(targetUsers);
        FileDB fileDB = fileDBRepository.findByName(postRequest.getFileName());
        Post post = postRepository.save(postMapper.map(postRequest, subreddit, authService.getCurrentUser(), targetUsers, fileDB));
        System.out.println(post);
        PostNotification postNotification = new PostNotification();
        
        
        
        postNotification.setMessage(authService.getCurrentUser().getUsername()+" posted a new post");
        postNotification.setVue(false);
        postNotification.setPostId(post.getPostId());
        postNotification.setSubredditId(post.getSubreddit().getId());
        postNotification.setUser(authService.getCurrentUser());
        postNotification.setCreatedDate(post.getCreatedDate());
        postNotification.setRole(post.getUser().getRoles().get(0));
        
        postNotificationService.save(postNotification);
        return post;
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream().sorted(Comparator.comparing(Post::getCreatedDate).reversed())
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }
    
    @Transactional(readOnly = true)
    public List<PostResponse> getPostsFromAdminAndFortargetUser(Long userId){
    	RoleName roleName = RoleName.valueOf("ROLE_ADMIN");
    	Role role = roleRepository.findByName(roleName).orElseThrow(() -> new SpringRedditException(""));
    	List<Role> roles = new ArrayList<Role>();
    	roles.add(role);
    	User user = userRepository.findByRoles(roles);
    	//<User> targetUsers = new ArrayList<>();
    	User targetUser = userRepository.findById(userId).orElseThrow(() -> new SpringRedditException("targetUser with id" +userId+" not found"));
    	//targetUsers.add(targetUser);
    	return postRepository.getPostsByUserAndTargetUsers(targetUser)
    			.stream()
    			.map(postMapper::mapToDto)
    			.collect(toList());
    }
    
    @Transactional
    public void deletePost(Long id) {
    	postRepository.deleteById(id); 
    }
    @Transactional
    public void removePost (Long postId) {
    	Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
    	List<Comment> commentList= commentRepository.findByPost(post);
    	List<Vote> votes = voteRepository.findAllByPost(post);
    	if(commentList.size()>0 || votes.size()<0) {
    		
    		
    		for(Vote vote :votes) {
    			post=vote.getPost();
    			vote.setPost(null);
    			
    			entityManager.remove(post);    				
    			   			
    		}
    		for(Comment comment :commentList) {
    			post=comment.getPost();
    			comment.setPost(null);
    			entityManager.remove(post);
    		}
    		
    	}else {
    		postRepository.deleteById(postId);
    	}
    }
}
