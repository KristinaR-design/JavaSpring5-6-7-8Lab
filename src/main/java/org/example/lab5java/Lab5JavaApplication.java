package org.example.lab5java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("org.example.lab5java.classes.repositories")
public class Lab5JavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(Lab5JavaApplication.class, args);
    }

}
