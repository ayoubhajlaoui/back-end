package com.programming.techie.springinternsmanager.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programming.techie.springinternsmanager.model.User;
import com.programming.techie.springinternsmanager.service.AccountManagerService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@RestController
@AllArgsConstructor
@RequestMapping("/api/accountmanager")
public class AccountManager {
	
	private final AccountManagerService accountManagerService;
	

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers () {
        List<User> users = accountManagerService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/find/{username}")
    public ResponseEntity<User> getUserById (@PathVariable String  username) {
        User user = accountManagerService.findUserById(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
         User newUser = accountManagerService.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    @Valid 
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updateUser = accountManagerService.updateUser(user);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @DeleteMapping("delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long userId) {
    	accountManagerService.removeUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
	
	


