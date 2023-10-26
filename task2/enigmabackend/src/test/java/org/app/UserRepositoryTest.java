package org.app;

import org.app.model.User;
import org.app.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepo userRepo;

    private User user;

    @BeforeEach
    public void initialData() {
        user = new User();
        user.setFirstName("john");
        user.setLastName("doe");
        user.setEmail("john@example.com");
    }
    @Test
    public void testCreateUser() {
        User savedUser = userRepo.save(user);

        assertNotNull(savedUser);
        assertEquals("john", savedUser.getFirstName());
        assertEquals("doe", savedUser.getLastName());
        assertEquals("john@example.com", savedUser.getEmail());
    }

    @Test
    public void testFindById() {
        User returnedTask = userRepo.save(user);

        User foundUser = userRepo.findUserById(returnedTask.getId()+1).orElse(null);
        assertNull(foundUser);
        foundUser = userRepo.findUserById(returnedTask.getId()).orElse(null);

        assertNotNull(foundUser);
        assertEquals("john", foundUser.getFirstName());
        assertEquals("doe", foundUser.getLastName());
        assertEquals("john@example.com", foundUser.getEmail());
    }

    @Test
    public void findByFilter(){
        userRepo.save(user);

        List<User> foundUsers = userRepo.findUserByFilter("not_match","","");
        assertNotNull(foundUsers);
        assertEquals(foundUsers.size(), 0);

        foundUsers = userRepo.findUserByFilter("","",""); //match any
        assertNotNull(foundUsers);
        assertEquals(foundUsers.size(), 1);
        assertEquals("john", foundUsers.get(0).getFirstName());
        assertEquals("doe", foundUsers.get(0).getLastName());
        assertEquals("john@example.com", foundUsers.get(0).getEmail());

        foundUsers = userRepo.findUserByFilterAssigned("","","", 0L);
        assertNotNull(foundUsers);
        assertEquals(foundUsers.size(), 0);
    }
}

