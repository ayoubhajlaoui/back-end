package com.programming.techie.springinternsmanager.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programming.techie.springinternsmanager.dto.SubredditDto;
import com.programming.techie.springinternsmanager.exceptions.SpringRedditException;
import com.programming.techie.springinternsmanager.mapper.SubredditMapper;
import com.programming.techie.springinternsmanager.model.Post;
import com.programming.techie.springinternsmanager.model.Subreddit;
import com.programming.techie.springinternsmanager.model.User;
import com.programming.techie.springinternsmanager.repository.PostRepository;
import com.programming.techie.springinternsmanager.repository.SubredditRepository;
import com.programming.techie.springinternsmanager.repository.UserRepository;

import java.util.List;

import javax.persistence.EntityManager;

import static java.util.stream.Collectors.toList;

import java.util.Comparator;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;
    private final UserRepository userRepository;
    private final EntityManager entityManager;
    private final PostRepository postRepository;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
        subredditDto.setId(save.getId());
        return subredditDto;
    }

    @Transactional
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream().sorted(Comparator.comparing(Subreddit::getCreatedDate).reversed())
                .map(subredditMapper::mapSubredditToDto)
                .collect(toList());
    }

    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("No subreddit found with ID - " + id));
        return subredditMapper.mapSubredditToDto(subreddit);
    }
    
    public SubredditDto getSubredditByName(String name) {
        Subreddit subreddit = subredditRepository.findByName(name)
                .orElseThrow(() -> new SpringRedditException("No subreddit found with name - " + name));
        return subredditMapper.mapSubredditToDto(subreddit);
    }
    
    public void deleteSubreddit(Long id) {
    	subredditRepository.deleteById(id); 
    }
    
    public List<SubredditDto> getSubredditsByUsername (String username){
    	User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("No user found with name - " + username));
    	return subredditRepository.findAllByUser(user)
    			.stream()
    			.map(subredditMapper::mapSubredditToDto)
    			.collect(toList());
    	
    }
    
    public void removeSubreddit (Long id) {
    	Subreddit subreddit = subredditRepository.findById(id).orElseThrow(() -> new SpringRedditException("No subreddit found with id - " + id));
    	List<Post> posts = postRepository.findAllBySubreddit(subreddit);
    	
    	for(Post post: posts) {
    		
    		post.setSubreddit(null);
    		
    	}
    	deleteSubreddit(id);
    }
}
