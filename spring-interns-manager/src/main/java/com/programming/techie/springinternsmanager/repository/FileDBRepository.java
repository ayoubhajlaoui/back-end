package com.programming.techie.springinternsmanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.programming.techie.springinternsmanager.model.FileDB;
import com.programming.techie.springinternsmanager.model.Post;
import com.programming.techie.springinternsmanager.model.User;


@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {
	
	
	List<FileDB> findAllByUser(User user);
	Optional<FileDB>  findByFileId(String id);
	FileDB findByName(String name );
	FileDB findByPost (Post post);
}
