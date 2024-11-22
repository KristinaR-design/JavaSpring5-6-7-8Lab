package org.example.lab5java.classes.repositories;

import org.example.lab5java.classes.Category;
import org.example.lab5java.classes.Task;
import org.example.lab5java.classes.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUsers(Users user);

    Page<Task> findByTitleContaining(String title, Pageable pageable);
    Page<Task> findAll(Pageable pageable);
    Page<Task> findByUsers(Users user, Pageable pageable);
    Page<Task> findByUsersAndTitleContaining(Users user, String title, Pageable pageable);
}