package com.programming.techie.springinternsmanager.model;

import static javax.persistence.FetchType.LAZY;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "files")
public class FileDB {
      @Id
	  @GeneratedValue(generator = "uuid")
	  @GenericGenerator(name = "uuid", strategy = "uuid2")
	  private String fileId;

	  private String name;

	  private String type;

	  @Lob
	  private byte[] data;
	  
	 	  
	  @ManyToOne(fetch = FetchType.LAZY)//@JsonIgnore 
	  @JoinColumn(name="user_id", referencedColumnName = "userId")
	  private User user; 
	 
	  @OneToOne(mappedBy="file")
	  private Post post; 
	  
	  
	  public FileDB(String name, String type, byte[] data, User user, Post post) {
		    this.name = name;
		    this.type = type;
		    this.data = data;
		    this.post = post;
		    this.user = user;
		  }
	
}
  