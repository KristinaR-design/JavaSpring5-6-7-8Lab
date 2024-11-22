package org.example.lab5java.classes.controllers;


import org.example.lab5java.classes.Task;
import org.example.lab5java.classes.Users;
import org.example.lab5java.classes.servises.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AminController {


    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private EmailService emailService;


    // Display tasks with pagination and search
    @GetMapping
    public String listTasks(Model model, Principal principal,
                            @RequestParam(value = "page", defaultValue = "0") int page,
                            @RequestParam(value = "size", defaultValue = "10") Integer size,
                            @RequestParam(value = "search", required = false) String search) {

        Users currentUser = userService.findByUsername(principal.getName());
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> tasksPage;

        // Admin sees all tasks, regular user sees only their own tasks
        if (search != null && !search.isEmpty()) {
            tasksPage = currentUser.isAdmin() ? taskService.findByTitleContaining(search, pageable) : taskService.findByUserAndTitleContaining(currentUser, search, pageable);
        } else {
            tasksPage = currentUser.isAdmin() ? taskService.findAll(pageable) : taskService.findByUser(currentUser, pageable);
        }

        model.addAttribute("tasks", tasksPage.getContent());
        model.addAttribute("totalPages", tasksPage.getTotalPages());
        model.addAttribute("currentPage", tasksPage.getNumber());
        model.addAttribute("search", search);
        model.addAttribute("categories", categoryService.findAll());
        return "admin";
    }

    @GetMapping("/addTask")
    public String addTasks(Model model) {
        model.addAttribute("task", new Task()); // Add this line
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("users", userService.findAll());
        return "addTask";
    }

    @PostMapping("/addTask")
    public String addTask(@ModelAttribute("task") Task task, Principal principal) {
        Users adminUser = userService.findByUsername(principal.getName());

        if (adminUser.isAdmin()) {
            taskService.save(task);
        }
        Users user = userService.findByUsername(task.getUsers().getUsername());
        emailService.sendTaskNotification(
                user.getEmail(),
                "Task Notification",
                "You have a new task assigned by the admin."
        );

        return "redirect:/admin";
    }



    @GetMapping("/new")
    public String showAddTaskForm(Model model, Principal principal) {
        Users currentUser = userService.findByUsername(principal.getName());

        if (!currentUser.isAdmin()) {
            return "redirect:/admin";  // Non-admin users cannot access the form
        }

        model.addAttribute("task", new Task());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("users", userService.findAll());
        return "addTask";
    }

    @GetMapping("/edit/{id}")
    public String showEditTaskForm(@PathVariable Long id, Model model, Principal principal) {
        Task task = taskService.findById(id);
        Users user = userService.findByUsername(principal.getName());

        if (task == null || (!user.isAdmin() && !task.getUsers().getId().equals(user.getId()))) {
            return "redirect:/admin";
        }

        model.addAttribute("task", task);
        model.addAttribute("categories", categoryService.findAll());
        return "editTask";
    }

    // Update task
    @PostMapping("/edit/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute Task task, Principal principal) {
        Task existingTask = taskService.findById(id);
        Users user = userService.findByUsername(principal.getName());

        if (existingTask == null || (!user.isAdmin() && !existingTask.getUsers().getId().equals(user.getId()))) {
            return "redirect:/admin";
        }

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setStatus(task.getStatus());
        existingTask.setPriority(task.getPriority());

        taskService.save(existingTask);
        return "redirect:/admin";
    }

    // Delete task
    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id, Principal principal) {
        Task task = taskService.findById(id);
        Users user = userService.findByUsername(principal.getName());

        if (task != null && (user.isAdmin() || task.getUsers().getId().equals(user.getId()))) {
            taskService.delete(task.getId());
        }

        return "redirect:/admin";
    }

}





