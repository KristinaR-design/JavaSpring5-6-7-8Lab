package org.example.lab5java.classes;


import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
@Entity
@Table(name = "users")
public class Users {


    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;
    private String password;
    private String email;


    @Column(name = "profile_image")
    private String profileImage;

    private Boolean admin = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Users() {
    }

    public Users(String username, String password, String email, String profileImage, Boolean admin, LocalDateTime createdAt) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.profileImage = profileImage;
        this.admin = admin;
        this.createdAt = createdAt;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setId(Long id) {this.id = id;}

    public Long getId() {return id;  }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getConfirmPassword() {
        return password;
    }

    public void setProfilePicture(String imageUrl) {
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}



