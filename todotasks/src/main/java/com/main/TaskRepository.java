package com.main;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
	
	ArrayList<Task> findAll();

	Optional<Task> findByName(String taskName);

	boolean existsByName(String taskName);

	ArrayList<Task> findAllByOrderByStartDateDesc();

	



}