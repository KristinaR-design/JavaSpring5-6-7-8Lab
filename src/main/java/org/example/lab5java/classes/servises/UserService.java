package org.example.lab5java.classes.servises;

import org.example.lab5java.classes.Users;
import org.example.lab5java.classes.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Users save(Users user) {
        user.setCreatedAt(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Users findByUsername(String username) {
        return userRepository.findByUsername(username);

    }

    public List<Users> findAll() {
        return userRepository.findAll();
    }


    public String resetPassword(Users user) {
        String newPassword = UUID.randomUUID().toString().substring(0, 8); // Generates random password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);  // Save new encoded password to the database
        return newPassword;      // Return plain password to email it
    }

    public Users getCurrentUser(String username){

        return userRepository.findByUsername(username);

    }

    public Users findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void saveUser(Users user) {
        userRepository.save(user);
    }


}
