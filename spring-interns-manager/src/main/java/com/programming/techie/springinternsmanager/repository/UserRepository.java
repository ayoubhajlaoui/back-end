package com.programming.techie.springinternsmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.programming.techie.springinternsmanager.model.Role;
import com.programming.techie.springinternsmanager.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    
    void deleteByUserId(Long id);
    
    User findByRoles(List<Role> roles);

    Optional<User> findEmployeeByUserId(Long id);
}
