package org.app;

import org.app.model.ActionResponse;
import org.app.model.Task;
import org.app.model.TaskInputModel;
import org.app.model.User;
import org.app.model.UserFilter;
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
public class UserServiceTest {
    @Autowired
    private UserService userService;

    private UserInputModel user;

    @BeforeEach
    public void initialData(){
        userService.removeAll();
        user = new UserInputModel();
        user.setFirstName("john");
        user.setLastName("doe");
        user.setEmail("john@example.com");
    }
    @Test
    public void testCRUD() {
        User newUser = userService.addUser(user);                               //Create

        assertNotNull(newUser);
        assertEquals("john", newUser.getFirstName());
        assertEquals("doe", newUser.getLastName());
        assertEquals("john@example.com", newUser.getEmail());

        User savedUser = userService.findUserById(newUser.getId());             //Read

        assertNotNull(newUser);
        assertEquals("john", newUser.getFirstName());
        assertEquals("doe", newUser.getLastName());
        assertEquals("john@example.com", newUser.getEmail());

        user.setId(savedUser.getId());
        user.setFirstName("oscar");
        user.setLastName("rambo");
        user.setEmail("mail@e.com");

        savedUser = userService.updateUser(user);

        assertNotNull(savedUser);
        assertEquals("oscar", savedUser.getFirstName());
        assertEquals("rambo", savedUser.getLastName());
        assertEquals("mail@e.com", savedUser.getEmail());

        ActionResponse response = userService.deleteUser(user);                 //Delete
        assertNotNull(response);
        assertEquals("User deleted", response.getMessage());
        assertEquals(HttpStatus.OK, response.getHttpCode());
    }

    @Test
    public void testFindByFilter() {
        userService.addUser(user);
        user.setFirstName("aaa");
        user.setLastName("bbb");
        user.setEmail("aaa@example.com");
        userService.addUser(user);
        user.setFirstName("aaabbb");
        user.setLastName("bbbccc");
        user.setEmail("test_email@example.com");
        userService.addUser(user);

        List<User> userList = userService.findAllUsers();
        assertNotNull(userList);
        assertEquals(3, userList.size());

        UserFilter filter = new UserFilter();
        filter.setFirstName("aaa");

        userList = userService.findByCriteria(filter);
        assertNotNull(userList);
        assertEquals(2, userList.size());

        filter.setFirstName("aaab");

        userList = userService.findByCriteria(filter);
        assertNotNull(userList);
        assertEquals(1, userList.size());

        filter.setFirstName("aaa");
        filter.setLastName("ccc");

        userList = userService.findByCriteria(filter);
        assertNotNull(userList);
        assertEquals(1, userList.size());

        assertEquals("aaabbb", userList.get(0).getFirstName());
        assertEquals("bbbccc", userList.get(0).getLastName());
        assertEquals("test_email@example.com", userList.get(0).getEmail());
    }
}

