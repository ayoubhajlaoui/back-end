package com.programming.techie.springinternsmanager.model;

import lombok.AllArgsConstructor; 
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long userId;
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    @Column(updatable = false)
    private String password;
    @Email
    @NotEmpty(message = "Email is required")
    private String email;
    
    
    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(
       name="user_role",
       joinColumns = @JoinColumn(name = "user_id"),
       inverseJoinColumns = @JoinColumn(name = "id"))
    private List<Role> roles = new ArrayList<>();
    
    
    //@ManyToMany(mappedBy="targetUsers")
    //private List<Post> sentPosts; 
     
    
//    @OneToMany( mappedBy="user", cascade = CascadeType.ALL,
//            orphanRemoval = true )
//   
//    
//    private List<FileDB> files = new ArrayList<>();
    @OneToMany
    private List<Subreddit> subreddits = new ArrayList<Subreddit>();
    
    @ManyToOne
    private Post post;
    
    private Instant created;
    private String imageUrl;
    private String projectTitle;
    private String phone;
    private boolean enabled;
    
}
