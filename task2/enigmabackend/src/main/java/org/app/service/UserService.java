package org.app.service;

import org.app.model.ActionResponse;
import org.app.model.Task;
import org.app.model.User;
import org.app.model.UserFilter;
import org.app.model.UserInputModel;
import org.app.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    public User addUser(UserInputModel user){
        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        return userRepo.save(newUser);
    }
    public User updateUser(UserInputModel user){
        User newUser = this.findUserById(user.getId());
        if(user.getFirstName() != null)
            newUser.setFirstName(user.getFirstName());
        if(user.getLastName() != null)
            newUser.setLastName(user.getLastName());
        if(user.getEmail() != null)
            newUser.setEmail(user.getEmail());
        return userRepo.save(newUser);
    }

    public User updateUser(User user){
        return userRepo.save(user);
    }

    @Transactional
    public ActionResponse deleteUser(UserInputModel user) {
        ActionResponse response = new ActionResponse();

        try{
            User userToRemove = this.findUserById(user.getId());
            if(!userToRemove.getAssignedTasks().isEmpty()){
                Long[] idArray = userToRemove.getAssignedTasks().stream()
                        .map(Task::getId)
                        .toArray(Long[]::new);
                response.setMessage("User has assigned tasks, he cannot be deleted. Assigned task IDs: "+ Arrays.toString(idArray));
                response.setHttpCode(HttpStatus.BAD_REQUEST);
            } else {
                userRepo.deleteUserById(user.getId());
                response.setMessage("User deleted");
                response.setHttpCode(HttpStatus.OK);
            }
        } catch (Exception e){
            response.setMessage("User with given ID not found");
            response.setHttpCode(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    public List<User> findAllUsers(){
        return userRepo.findAll();
    }

    public User findUserById(Long id) {
        return userRepo.findUserById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<User> findByCriteria(UserFilter filter){
        if(filter.getProjectId() == null)
            return userRepo.findUserByFilter(filter.getFirstName(), filter.getLastName(), filter.getEmail());
        else //operation splited due to data model, finding by Task ID requires join
            return userRepo.findUserByFilterAssigned(filter.getFirstName(), filter.getLastName(), filter.getEmail(), filter.getProjectId());
    }

    public void removeAll(){ //for testing
        this.userRepo.deleteAll();
    }

}
