package com.programming.techie.springinternsmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.programming.techie.springinternsmanager.dto.SubredditDto;
import com.programming.techie.springinternsmanager.model.Subreddit;
import com.programming.techie.springinternsmanager.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubredditRepository extends JpaRepository<Subreddit, Long> {

    Optional<Subreddit> findByName(String subredditName);
    void deleteById(Long id);
    List<Subreddit> findAllByUser(User user);
}
