package org.app.model;

import java.time.LocalDate;
import java.util.Set;

public class TaskInputModel {
    private Long id;
    private String title;
    private String description;
    private String status;
    private LocalDate deadline;
    Set<Integer> assignedUsers;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Set<Integer> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(Set<Integer> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
