package org.app.controllers;

import org.app.model.ActionResponse;
import org.app.model.User;
import org.app.model.UserFilter;
import org.app.model.UserInputModel;
import org.app.service.UserService;
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
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){this.userService=userService;}

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/add")
    public User addUser(@RequestBody UserInputModel user){
        return userService.addUser(user);
    }

    @PutMapping("/edit")
    public User editUser(@RequestBody UserInputModel user){
        return userService.updateUser(user);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> deleteUser(@RequestBody UserInputModel user){
        ActionResponse status = userService.deleteUser(user);
        return new ResponseEntity<>(status.getMessage(), status.getHttpCode());
    }
    @GetMapping("/search")
    public ResponseEntity<List<User>> findByCriteria(@RequestBody UserFilter filter){
        List<User> users = userService.findByCriteria(filter);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
