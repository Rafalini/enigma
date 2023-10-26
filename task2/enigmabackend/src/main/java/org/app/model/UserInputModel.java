package org.app.model;

import java.util.Set;

public class UserInputModel {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<Integer> assignedTaskIds;
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Integer> getAssignedTaskIds() {
        return assignedTaskIds;
    }

    public void setAssignedTaskIds(Set<Integer> assignedTaskIds) {
        this.assignedTaskIds = assignedTaskIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
