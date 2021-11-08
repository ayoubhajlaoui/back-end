package com.programming.techie.springinternsmanager.model;

import static javax.persistence.FetchType.LAZY; 
import static javax.persistence.GenerationType.IDENTITY;

import java.time.Instant;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.ser.std.SerializableSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private long id;
	private String name;
	private String description;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dueDate;
	private String type;
	
	@ManyToOne(fetch = LAZY, cascade = CascadeType.ALL) 
	@JoinColumn(name = "userId", referencedColumnName = "userId")
	@JsonIgnore 
	//@JsonManagedReference
	private User user;
	
	
	//@ManyToOne
	//private Program program;

}
