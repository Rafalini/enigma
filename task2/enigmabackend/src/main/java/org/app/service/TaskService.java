package org.app.service;

import org.app.model.ActionResponse;
import org.app.model.Task;
import org.app.model.TaskFilter;
import org.app.model.User;
import org.app.model.TaskAssignment;
import org.app.model.TaskInputModel;
import org.app.repository.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service

public class TaskService {
    private final TaskRepo taskRepo;

    private final UserService userService;

    @Autowired
    public TaskService(TaskRepo taskRepo, UserService userService) {
        this.taskRepo = taskRepo;
        this.userService = userService;
    }

    public Task addTask(TaskInputModel task) {
        Task newTask = new Task();
        newTask.setTitle(task.getTitle());
        newTask.setDescription(task.getDescription());
        newTask.setStatus(task.getStatus());
        newTask.setDeadline(task.getDeadline());

        if(task.getAssignedUsers() != null){
            for (int id : task.getAssignedUsers())
                try {
                    newTask.addUser(userService.findUserById((long) id));
                } catch (Exception ignored) {}
        }

        return taskRepo.save(newTask);
    }

    public Task updateTask(TaskInputModel task) {
        Task newTask = this.findTaskById(task.getId());

        if (task.getTitle() != null)
            newTask.setTitle(task.getTitle());
        if (task.getDescription() != null)
            newTask.setDescription(task.getDescription());
        if (task.getStatus() != null)
            newTask.setStatus(task.getStatus());
        if (task.getDeadline() != null)
            newTask.setDeadline(task.getDeadline());
        if(task.getAssignedUsers() != null) {
            for (int id : task.getAssignedUsers())
                try {
                    newTask.addUser(userService.findUserById((long) id));
                } catch (Exception ignored) {
                } //if no such user it wont be added
        }
        return taskRepo.save(newTask);
    }

    public Task updateTask(Task task){
        return taskRepo.save(task);
    }

    public ActionResponse deleteTask(TaskInputModel task) {
        ActionResponse response = new ActionResponse();

        if (taskRepo.findById(task.getId()).isEmpty()) {
            response.setMessage("Task with given ID not found");
            response.setHttpCode(HttpStatus.BAD_REQUEST);
        } else {
            taskRepo.deleteById(task.getId());
            response.setMessage("Task deleted");
            response.setHttpCode(HttpStatus.OK);
        }
        return response;
    }

    public List<Task> findAllTasks() {
        return taskRepo.findAll();
    }

    public Task findTaskById(Long id) {
        return taskRepo.findTaskById(id)
                .orElseThrow(EntityNotFoundException::new);

    }

    public ActionResponse assignTaskToUser(TaskAssignment taskAssignment) {
        ActionResponse response = new ActionResponse();

        try{
            Task assignedTask = findTaskById(taskAssignment.getTaskId());
            User user = userService.findUserById(taskAssignment.getUserId());
            assignedTask.addUser(user);
            taskRepo.save(assignedTask);
            response.setMessage("Task assigned");
            response.setHttpCode(HttpStatus.OK);
        } catch(Exception e){
            response.setMessage("ID's were incorrect, some entities couldn't be found");
            response.setHttpCode(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    public ActionResponse removeAssignment(TaskAssignment taskAssignment) {
        ActionResponse response = new ActionResponse();

        try{
            Task assignedTask = findTaskById(taskAssignment.getTaskId());
            User user = userService.findUserById(taskAssignment.getUserId());
            assignedTask.removeUser(user);
            taskRepo.save(assignedTask);
            response.setMessage("Task assigned removed");
            response.setHttpCode(HttpStatus.OK);
        } catch(Exception e){
            response.setMessage("ID's were incorrect, some entities couldn't be found");
            response.setHttpCode(HttpStatus.BAD_REQUEST);
        }
        return response;
    }
    @Transactional
    public List<Task> findByCriteria(TaskFilter filter){
        if(filter.getUserId() == null)
            return taskRepo.findTaskByFilter(filter.getTitle(), filter.getDescription(), filter.getStatus(), filter.getDateFrom(), filter.getDateTo());
        else //operation splited due to data model, finding by User ID requires join
            return taskRepo.findTaskByFilterAssigned(filter.getTitle(), filter.getDescription(), filter.getStatus(), filter.getDateFrom(), filter.getDateTo(), filter.getUserId());
    }

    public void removeAll(){ //for testing
        this.taskRepo.deleteAll();
    }
}
