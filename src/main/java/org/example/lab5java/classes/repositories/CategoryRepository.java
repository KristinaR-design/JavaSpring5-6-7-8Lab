package org.example.lab5java.classes.repositories;

import org.example.lab5java.classes.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.name IN ('Educational')")
    List<Category> findBySpecificNames();
}