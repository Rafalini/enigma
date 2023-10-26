package org.app;

import org.app.model.ActionResponse;
import org.app.model.Task;
import org.app.model.TaskAssignment;
import org.app.model.TaskFilter;
import org.app.model.TaskInputModel;
import org.app.model.User;
import org.app.model.UserInputModel;
import org.app.service.TaskService;
import org.app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TaskServiceTest {
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    private TaskInputModel task;

    String dateStr = "2020-01-08"; //any valid date here
    @BeforeEach
    public void initServices(){
        taskService.removeAll();
        userService.removeAll();
        task = new TaskInputModel();
        task.setTitle("task1");
        task.setDescription("john@example.com");
        task.setDeadline(LocalDate.parse(dateStr));
    }
    @Test
    public void testCRUD() {
        Task newTask = taskService.addTask(task);                               //Create

        assertNotNull(newTask);
        assertEquals("task1", newTask.getTitle());
        assertEquals("john@example.com", newTask.getDescription());
        assertEquals(LocalDate.parse(dateStr), newTask.getDeadline());

        Task savedTask = taskService.findTaskById(newTask.getId());             //Read

        assertNotNull(newTask);
        assertEquals("task1", newTask.getTitle());
        assertEquals("john@example.com", newTask.getDescription());
        assertEquals(LocalDate.parse(dateStr), newTask.getDeadline());

        List<Task> taskList = taskService.findAllTasks();
        assertNotNull(taskList);
        assertEquals(1, taskList.size());

        task.setId(savedTask.getId());
        task.setTitle("new title");
        task.setDescription("updated description");
        task.setDeadline(LocalDate.parse(dateStr).plusDays(1));

        savedTask = taskService.updateTask(task);                               //Update

        assertNotNull(newTask);
        assertEquals("new title", savedTask.getTitle());
        assertEquals("updated description", savedTask.getDescription());
        assertEquals(LocalDate.parse(dateStr).plusDays(1), savedTask.getDeadline());

        ActionResponse response = taskService.deleteTask(task);                 //Delete
        assertNotNull(response);
        assertEquals("Task deleted", response.getMessage());
        assertEquals(HttpStatus.OK, response.getHttpCode());
    }

    @Test
    public void testAssignAndRemove() {
        UserInputModel user = new UserInputModel();
        user.setFirstName("john");
        user.setLastName("doe");
        user.setEmail("john@example.com");
        userService.addUser(user);
        user.setFirstName("jack");
        user.setLastName("sparrow");
        user.setEmail("captain@ship.com");
        User savedUser = userService.addUser(user);

        Task savedTask = taskService.addTask(task);
        assertNotNull(savedTask);
        assertEquals(0, savedTask.getAssignedUsers().size());

        savedTask.addUser(savedUser);
        Task savedTask1 = taskService.updateTask(savedTask);

        assertNotNull(savedTask);
        assertEquals(1, savedTask1.getAssignedUsers().size());

        savedTask.removeUser(savedUser);
        Task savedTask0 = taskService.updateTask(savedTask);
        assertNotNull(savedTask0);
        assertEquals(0, savedTask0.getAssignedUsers().size());
    }

    @Test
    public void testFindByFilter() {
        taskService.addTask(task);
        task.setTitle("task2");
        task.setDescription("test description 2");
        task.setDeadline(LocalDate.parse(dateStr).plusDays(1));
        taskService.addTask(task);
        task.setTitle("task3");
        task.setDescription("test description 3");
        task.setDeadline(LocalDate.parse(dateStr).plusDays(2));
        taskService.addTask(task);

        List<Task> taskList = taskService.findAllTasks();
        assertNotNull(taskList);
        assertEquals(3, taskList.size());


        TaskFilter filter = new TaskFilter();

        taskList = taskService.findByCriteria(filter);
        assertNotNull(taskList);
        assertEquals(3, taskList.size());

        filter.setTitle("task");

        taskList = taskService.findByCriteria(filter);
        assertNotNull(taskList);
        assertEquals(3, taskList.size());

        filter.setTitle("task");
        filter.setDescription("3");

        taskList = taskService.findByCriteria(filter);
        assertNotNull(taskList);
        assertEquals(1, taskList.size());
        assertEquals("task3", taskList.get(0).getTitle());
        assertEquals("test description 3", taskList.get(0).getDescription());
        assertEquals(LocalDate.parse(dateStr).plusDays(2), taskList.get(0).getDeadline());

        filter.setTitle(null);
        filter.setDescription(null);
        filter.setDateFrom(LocalDate.parse(dateStr).plusDays(1));
        filter.setDateTo(LocalDate.parse(dateStr).plusDays(2));
        taskList = taskService.findByCriteria(filter);
        assertNotNull(taskList);
        assertEquals(2, taskList.size());
    }
}

