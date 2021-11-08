package com.programming.techie.springinternsmanager.dto;

import org.apache.logging.log4j.CloseableThreadContext.Instance;
import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProgramDto {
	private Long id;
	private String name;
    private String description;
    private Integer numberOfTasks;
    private Date startDate;
	private Date finishDate;
	
}