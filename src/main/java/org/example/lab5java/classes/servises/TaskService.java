package org.example.lab5java.classes.servises;

import org.example.lab5java.classes.Task;
import org.example.lab5java.classes.repositories.TaskRepository;
import org.example.lab5java.classes.repositories.UserRepository;
import org.example.lab5java.classes.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getUserTasks(Users user) {
        return taskRepository.findByUsers(user);
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public void delete(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public Task findById(Long taskId) {
            return taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Invalid task ID: " + taskId));
    }


    public List<Task> findByUsers(Users user) {
        return taskRepository.findByUsers(user);
    }


    public List<Task> findAll() {
        return taskRepository.findAll();
    }
    

    // Фильтрация задач по названию
    public Page<Task> findByTitleContaining(String title, Pageable pageable) {
        return taskRepository.findByTitleContaining(title, pageable);
    }

    // Получение всех задач с пагинацией
    public Page<Task> findAll(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    // Получение задач, принадлежащих конкретному пользователю
    public Page<Task> findByUser(Users user, Pageable pageable) {
        return taskRepository.findByUsers(user, pageable);
    }


    public Page<Task> findByUserAndTitleContaining(Users currentUser, String search, Pageable pageable) {
        return taskRepository.findByUsersAndTitleContaining(currentUser, search, pageable);
    }
}

