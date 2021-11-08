package com.programming.techie.springinternsmanager.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.programming.techie.springinternsmanager.dto.FileDto;
import com.programming.techie.springinternsmanager.dto.TaskDto;
import com.programming.techie.springinternsmanager.exceptions.SpringRedditException;
import com.programming.techie.springinternsmanager.model.FileDB;
import com.programming.techie.springinternsmanager.model.Post;
import com.programming.techie.springinternsmanager.model.Task;
import com.programming.techie.springinternsmanager.model.User;
import com.programming.techie.springinternsmanager.repository.FileDBRepository;
import com.programming.techie.springinternsmanager.repository.PostRepository;
import com.programming.techie.springinternsmanager.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FileStorageService {

	
  @Autowired
  private FileDBRepository fileDBRepository;
  private AuthService authService; 
  private PostRepository postRepository;
  private UserRepository userRepository;

  public FileDB store(MultipartFile file) throws IOException {
	  //String userName=file.getName();
	  
	  return fileDBRepository.save(mapToFileDB(file));
  }
  //@Value("${files.path}")
  public static final String DIRECTORY = System.getProperty("user.home") + "/Downloads/uploads/";
  public Resource download(String filename) {
      try {
          Path file = Paths.get(DIRECTORY)
                           .resolve(filename);
          Resource resource = new UrlResource(file.toUri());

          if (resource.exists() || resource.isReadable()) {
              return resource;
          } else {
              throw new RuntimeException("Could not read the file!");
          }
      } catch (MalformedURLException e) {
          throw new RuntimeException("Error: " + e.getMessage());
      }
  }

  public Stream<FileDB> getFilesByUser(String username) {
	  User user = userRepository.findByUsername(username).orElseThrow();
    return fileDBRepository.findAllByUser(user).stream();
  }
  
  public Stream<FileDB> getFileByFileId(String id) {
	    return fileDBRepository.findByFileId(id).stream();
	  }
  
  public FileDB getFileByPostId(Long postId) {
	  Post post = postRepository.findById(postId).orElseThrow();
	    return fileDBRepository.findByPost(post);
	  }
  
  
  public Stream<FileDB> getAllFiles() {
    return fileDBRepository.findAll().stream();
  }
  
  private FileDB mapToFileDB(MultipartFile file) throws IOException {
		
		return FileDB.builder()
				.name(StringUtils.cleanPath(file.getOriginalFilename()))
				.type(file.getContentType())
				.user(authService.getCurrentUser())
				.data(file.getBytes())
				
				.build();
	}
 
  
  
  
  
}