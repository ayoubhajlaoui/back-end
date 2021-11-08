package com.programming.techie.springinternsmanager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long postId;
    @NotBlank(message = "Post Name cannot be empty or Null")
    private String postName;
    @Nullable
    private String url;
    @Nullable
    @Lob
    private String description;
    private Integer voteCount = 0;
    @ManyToOne(fetch = LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
    private Instant createdDate;
    @ManyToOne(fetch = LAZY,cascade = CascadeType.DETACH)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Subreddit subreddit;
    //@ManyToMany
    //@JoinTable(joinColumns=@JoinColumn(name="post_id"),inverseJoinColumns=@JoinColumn(name="user_id"))
    //private List<User> targetUsers;  
    
    @OneToMany
    @JoinColumn(name = "user_id0")
    private List<User> targetUsers;
    
    
	  @OneToOne(fetch = LAZY, cascade = CascadeType.DETACH)  
	    @JoinColumn(name = "fileId", referencedColumnName = "fileId")
	  FileDB file;
	  
    //@OneToOne(mappedBy="post")
    //private FileDB file;
}
