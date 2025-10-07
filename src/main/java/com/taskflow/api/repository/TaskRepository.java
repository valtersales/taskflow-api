package com.taskflow.api.repository;

import com.taskflow.api.entity.Task;
import com.taskflow.api.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findByUser(User user, Pageable pageable);

    List<Task> findByUserAndStatus(User user, Task.Status status);

    @Query("SELECT t FROM Task t WHERE t.user = :user AND " +
            "(:status IS NULL OR t.status = :status) AND " +
            "(:priority IS NULL OR t.priority = :priority)")
    Page<Task> findByUserAndFilters(@Param("user") User user,
            @Param("status") Task.Status status,
            @Param("priority") Task.Priority priority,
            Pageable pageable);

    long countByUserAndStatus(User user, Task.Status status);
}
