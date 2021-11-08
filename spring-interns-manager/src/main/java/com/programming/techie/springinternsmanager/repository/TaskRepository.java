package com.programming.techie.springinternsmanager.repository;


import java.util.List;  

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.programming.techie.springinternsmanager.dto.CountType;
import com.programming.techie.springinternsmanager.model.Task;
import com.programming.techie.springinternsmanager.model.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	
	public List<Task> findAllByUser(User user);
	
	
	

	@Query(value="Select * from task order by due_date desc",nativeQuery = true)
	public List<Task> getAllTaskDueDateDesc();
	
	
	@Query(value="Select new com.programming.techie.springinternsmanager.dto.CountType(COUNT(*)/(Select COUNT(*) from Task WHERE user = ?1) *100,type) from Task WHERE user = ?1 GROUP BY type") 
	public List<CountType> getPercentageGroupByTypeByUser(User user);
	
	

}
