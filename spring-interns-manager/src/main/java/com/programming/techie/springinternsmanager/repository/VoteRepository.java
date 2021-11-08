package com.programming.techie.springinternsmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.programming.techie.springinternsmanager.model.Post;
import com.programming.techie.springinternsmanager.model.User;
import com.programming.techie.springinternsmanager.model.Vote;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
    List<Vote> findAllByUser(User user);
    List<Vote> findAllByPost(Post post);
}
