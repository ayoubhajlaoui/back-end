package com.programming.techie.springinternsmanager.model;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.apache.logging.log4j.CloseableThreadContext.Instance;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
@Entity
public class Program {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	private String name;
	private String description;
	private Date startDate;
	private Date finishDate;
	
	//@OneToOne
	//private User user;
	
	//@OneToMany
	//private List<Task> tasks;  

}
