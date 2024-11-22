package org.example.lab5java.classes.controllers;

import org.example.lab5java.classes.Users;
import org.example.lab5java.classes.repositories.UserRepository;
import org.example.lab5java.classes.servises.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    public UserRepository userRepository;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new Users());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute Users user) {
        userService.save(user);
        return "redirect:/login";
    }




    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
