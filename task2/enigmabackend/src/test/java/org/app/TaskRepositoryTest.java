package org.app;

import org.app.model.Task;
import org.app.model.TaskInputModel;
import org.app.repository.TaskRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
public class TaskRepositoryTest {
    @Autowired
    private TaskRepo taskRepo;
    private Task task;
    private String dateStr = "2020-01-08"; //any valid date here
    @BeforeEach
    public void initServices(){
        this.task = new Task();
        task.setTitle("task1");
        task.setDescription("john@example.com");
        task.setDeadline(LocalDate.parse(dateStr));
    }

    @Test
    public void testCreate() {
        Task savedTask = taskRepo.save(task);

        assertNotNull(savedTask);
        assertEquals("task1", savedTask.getTitle());
        assertEquals("john@example.com", savedTask.getDescription());
        assertEquals(LocalDate.parse(dateStr), savedTask.getDeadline());
    }

    @Test
    public void testFindById() {
        Task returnedTask = taskRepo.save(task);

        Task foundTask = taskRepo.findTaskById(returnedTask.getId()+1).orElse(null);
        assertNull(foundTask);
        foundTask = taskRepo.findTaskById(returnedTask.getId()).orElse(null);

        assertNotNull(foundTask);
        assertEquals("task1", foundTask.getTitle());
        assertEquals("john@example.com", foundTask.getDescription());
    }
}

