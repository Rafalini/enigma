package org.app.repository;

import org.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
        Optional<User> findUserById(Long id);
        void deleteUserById(Long id);


        @Query("select u from User u " +
                "join u.assignedTasks t where (:taskId = t.id) "+
                "and (:name is null or u.firstName LIKE %:name%) "+
                "and (:lastName is null or u.lastName LIKE %:lastName%) " +
                "and (:email is null or u.email LIKE %:email%) ")
        List<User> findUserByFilterAssigned(@Param("name") String name,
                                            @Param("lastName") String lastName,
                                            @Param("email") String email,
                                            @Param("taskId") Long taskId);

        @Query("select u from User u " +
                "where (:name is null or u.firstName LIKE %:name%) "+
                "and (:lastName is null or u.lastName LIKE %:lastName%) " +
                "and (:email is null or u.email LIKE %:email%) ")
        List<User> findUserByFilter(@Param("name") String name,
                                    @Param("lastName") String lastName,
                                    @Param("email") String email);
}
