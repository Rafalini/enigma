package org.app.repository;

import org.app.model.Task;
import org.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskRepo extends JpaRepository<Task, Long> {
    Optional<Task> findTaskById(Long id);
    void deleteById(Long id);

    @Query("select t from Task t " +
            "join t.assignedUsers u where (:userId = u.id) " +
            "and (:title is null or t.title LIKE %:title%) "+
            "and (:description is null or t.description LIKE %:description%) " +
            "and (:status is null or t.status LIKE %:status%) " +
            "and (:dateFrom is null or t.deadline >= :dateFrom) " +
            "and (:dateFrom is null or t.deadline <= :dateTo) ")
    List<Task> findTaskByFilterAssigned(@Param("title") String title,
                                        @Param("description") String description,
                                        @Param("status") String status,
                                        @Param("dateFrom") LocalDate dateFrom,
                                        @Param("dateTo") LocalDate dateTo,
                                        @Param("userId") Long userId);

    @Query("select t from Task t " +
            "where (:title is null or t.title LIKE %:title%) "+
            "and (:description is null or t.description LIKE %:description%) " +
            "and (:status is null or t.status LIKE %:status%) " +
            "and (:dateFrom is null or t.deadline >= :dateFrom) " +
            "and (:dateFrom is null or t.deadline <= :dateTo) ")
    List<Task> findTaskByFilter(@Param("title") String title,
                                @Param("description") String description,
                                @Param("status") String status,
                                @Param("dateFrom") LocalDate dateFrom,
                                @Param("dateTo") LocalDate dateTo);
}
