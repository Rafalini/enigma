package org.app.controllers;

import org.app.model.ActionResponse;
import org.app.model.Task;
import org.app.model.TaskAssignment;
import org.app.model.TaskFilter;
import org.app.model.TaskInputModel;
import org.app.model.User;
import org.app.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService){this.taskService=taskService;}

    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAllTasks(){
        List<Task> tasks = taskService.findAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PostMapping("/add")
    public Task addTask(@RequestBody TaskInputModel task){
        return taskService.addTask(task);
    }

    @PutMapping("/edit")
    public Task editTask(@RequestBody TaskInputModel task){
        return taskService.updateTask(task);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> deleteTask(@RequestBody TaskInputModel task){
        ActionResponse response = taskService.deleteTask(task);
        return new ResponseEntity<>(response.getMessage(), response.getHttpCode());
    }

    @PostMapping("/assign")
    public ResponseEntity<?> assignTask(@RequestBody TaskAssignment taskAssignment){
        ActionResponse response = taskService.assignTaskToUser(taskAssignment);
        return new ResponseEntity<>(response.getMessage(), response.getHttpCode());
    }

    @DeleteMapping("/remove-assignment")
    public ResponseEntity<?> removeAssignment(@RequestBody TaskAssignment taskAssignment){
        ActionResponse response = taskService.removeAssignment(taskAssignment);
        return new ResponseEntity<>(response.getMessage(), response.getHttpCode());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Task>> findByCriteria(@RequestBody TaskFilter filter){
        List<Task> tasks = taskService.findByCriteria(filter);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }
}
