package com.programming.techie.springinternsmanager.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.programming.techie.springinternsmanager.dto.ResponseFile;
import com.programming.techie.springinternsmanager.dto.ResponseMessage;
import com.programming.techie.springinternsmanager.model.FileDB;
import com.programming.techie.springinternsmanager.model.Post;
import com.programming.techie.springinternsmanager.repository.PostRepository;
import com.programming.techie.springinternsmanager.service.FileStorageService;



@RestController
@RequestMapping("/api/file/")
//@CrossOrigin("http://localhost:8080")
public class FileController {

  @Autowired
  private FileStorageService storageService;
  @Autowired
  private PostRepository postRepository;

  @PostMapping("/upload")
  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
    String message = "";
    try {
      storageService.store(file);

      message = "Uploaded the file successfully: " + file.getOriginalFilename();
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    } catch (Exception e) {
      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
  }
  
  @GetMapping("download/{filename}")
  @ResponseBody
  public ResponseEntity<Resource> downloadFile(@PathVariable String filename) throws IOException {
      Resource file = storageService.download(filename);
      Path path = file.getFile()
                      .toPath();

      return ResponseEntity.ok()
                           .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path))
                           .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                           .body(file);
  }

  @GetMapping("/files")
  public ResponseEntity<List<ResponseFile>> getListFiles() {
    List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
      String fileDownloadUri = ServletUriComponentsBuilder
          .fromCurrentContextPath()
          .path("/files/")
          .path(dbFile.getFileId())
          .toUriString();

      return new ResponseFile(
          dbFile.getName(),
          fileDownloadUri,
          dbFile.getType(),
          dbFile.getData().length);
    }).collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(files);
  }

//  @GetMapping("/files/{id}")
//  public ResponseEntity<byte[]> getFile(@PathVariable String id) {
//    FileDB fileDB = storageService.getFile(id);
//
//    return ResponseEntity.ok()
//        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
//        .body(fileDB.getData());
//  }
  
//  @GetMapping("/files/{post}")
//  public ResponseEntity<List<ResponseFile>> getListFilesByPost(@PathVariable Post post) {
//    List<ResponseFile> files = storageService.getFilesByPost(post).map(dbFile -> {
//      String fileDownloadUri = ServletUriComponentsBuilder
//          .fromCurrentContextPath()
//          .path("/files/")
//          .path(dbFile.getId())
//          .toUriString();
//
//      return new ResponseFile(
//          dbFile.getName(),
//          fileDownloadUri,
//          dbFile.getType(),
//          dbFile.getData().length);
//    }).collect(Collectors.toList());
//
//    return ResponseEntity.status(HttpStatus.OK).body(files);
//  }

  @GetMapping("/files/currentuser")
  public ResponseEntity<List<ResponseFile>> getCurrentFiles() {
	    List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
	      String fileDownloadUri = ServletUriComponentsBuilder
	          .fromCurrentContextPath()
	          .path("/files/")
	          .path(dbFile.getFileId())
	          .toUriString();

	      return new ResponseFile(
	          dbFile.getName(),
	          fileDownloadUri,
	          dbFile.getType(),
	          dbFile.getData().length);
	    }).collect(Collectors.toList());

	    return ResponseEntity.status(HttpStatus.OK).body(files);
	  }
  
  @GetMapping("/fileById/{id}")
  public ResponseEntity<List<ResponseFile>> getFileById(@PathVariable String id) {
    List<ResponseFile> files = storageService.getFileByFileId(id).map(dbFile -> {
      String fileDownloadUri = ServletUriComponentsBuilder
          .fromCurrentContextPath()
          .path("/files/")
          .path(dbFile.getFileId())
          .toUriString();

      return new ResponseFile(
          dbFile.getName(),
          fileDownloadUri,
          dbFile.getType(),
          dbFile.getData().length);
    }).collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(files);
  }
  
  @GetMapping("/fileByPostId/{postId}")
  public ResponseEntity<ResponseFile> getFileByPostId(@PathVariable Long postId) {
	
    FileDB file = storageService.getFileByPostId(postId);
      String fileDownloadUri = ServletUriComponentsBuilder
          .fromCurrentContextPath()
          .path("/files/")
          .path(file.getFileId())
          .toUriString();

      ResponseFile responseFile= new ResponseFile(
          file.getName(),
          fileDownloadUri,
          file.getType(),
          file.getData().length);
    
    return ResponseEntity.status(HttpStatus.OK).body(responseFile);
  }
  
  @GetMapping("/fileByUsername/{username}")
  public ResponseEntity<List<ResponseFile>> getFileByUsername(@PathVariable String username) {
    List<ResponseFile> files = storageService.getFilesByUser(username).map(dbFile -> {
      String fileDownloadUri = ServletUriComponentsBuilder
          .fromCurrentContextPath()
          .path("/files/")
          .path(dbFile.getFileId())
          .toUriString();

      return new ResponseFile(
          dbFile.getName(),
          fileDownloadUri,
          dbFile.getType(),
          dbFile.getData().length);
    }).collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(files);
  }
  

  
}