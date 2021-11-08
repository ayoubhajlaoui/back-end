package com.programming.techie.springinternsmanager.dto;

import org.apache.logging.log4j.CloseableThreadContext.Instance; 

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date; 

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDto {
	
	private Long id;
	private String name;
	private String description;
	//private String programName;
	private String type;
	private Date dueDate;
	private String username;

}
