package com.programming.techie.springinternsmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.programming.techie.springinternsmanager.model.Post;
import com.programming.techie.springinternsmanager.model.Subreddit;
import com.programming.techie.springinternsmanager.model.User;

import java.util.Collection;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
    
    
    @Query(value="FROM Post p  join p.targetUsers t WHERE t =:target ")
    List<Post> getPostsByUserAndTargetUsers(@Param("target")User targetUsers );
    
    //@Query(value="SELECT * FROM post p JOIN  p.targetUsers t WHERE t=?1", nativeQuery = true)
    //List<Post> getPostsByUserAndTargetUsers(User targetUsers );
    
    
  //@Query(value="FROM Post p  join p.user u WHERE u =:user")
  //List<Post> getPostsByUserAndTargetUsers(@Param("user")User User );
}
