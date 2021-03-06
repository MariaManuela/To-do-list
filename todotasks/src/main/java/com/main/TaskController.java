package com.main;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {
	
	@Autowired
	private TaskRepository taskRepository;
	
	@RequestMapping(value = "/added", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public boolean add(@RequestBody TaskForm taskForm) {
		if (taskForm == null) {
			return false;
		} else {
			boolean checkTaskExists = taskRepository.existsByName(taskForm.getTaskName());
			if(checkTaskExists) {
				return false;
			}else {
				Task addTask = new Task();
				addTask.setName(taskForm.getTaskName());
				addTask.setDone(false);
				addTask.setStartDate(new Date(System.currentTimeMillis()));
				taskRepository.save(addTask);
				return true;
			}
			
		}
		
	}
	
	@RequestMapping(value = "/tasks", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<Task> getTasks() {
		ArrayList<Task> tasks = taskRepository.findAllByOrderByStartDateDesc();
			return tasks;
	}

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE) 
    @ResponseBody
    public boolean deleteTask(@RequestParam(value = "name") String taskName) {

    		Optional<Task> optionalTask = taskRepository.findByName(taskName);
    		if(optionalTask.isPresent()) {
    			Task task = optionalTask.get();
    			taskRepository.delete(task);
    			return true;
    		}else {
    			return false;
    		}
    		
    }
    
	@RequestMapping(value = "/edit", method = RequestMethod.PUT, consumes = "application/json")
	@ResponseBody
	public boolean editTask(@RequestBody EditTaskDTO editTaskDTO) {
		Optional<Task> optionalTask = taskRepository.findByName(editTaskDTO.getOldName());
		if(optionalTask.isPresent()) {
			Task task = optionalTask.get();
			task.setName(editTaskDTO.getNewName());
			taskRepository.save(task);
			return true;
		}else {
			return true;
		}
	}
	
	@RequestMapping(value = "/check", method = RequestMethod.PUT)
	@ResponseBody
	public boolean checkTask(@RequestParam(name = "done") boolean isDone, @RequestParam(name = "name") String name) {
		Optional<Task> optionalTask = taskRepository.findByName(name);
		if(optionalTask.isPresent()) {
			Task task = optionalTask.get();
			task.setDone(isDone);
			taskRepository.save(task);
			return true;
		}else {
			return true;
		}
	}
    

  @RequestMapping(value = "/search", method=RequestMethod.GET)
  @ResponseBody
  public List<Task> searchTask(@RequestParam (name = "taskName") String name ) {

	  if(!taskRepository.findByNameLike(name).isEmpty()) {
		  return taskRepository.findByNameLike(name);
	  }else {
		  return new ArrayList<Task>();
	  }
		  
  }
}

	
   
    		


 


    

