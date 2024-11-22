package org.example.lab5java.classes.repositories;

import org.example.lab5java.classes.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.*;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
    Users findByEmail(String email);

}