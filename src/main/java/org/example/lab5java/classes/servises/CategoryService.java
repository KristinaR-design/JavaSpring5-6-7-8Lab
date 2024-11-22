package org.example.lab5java.classes.servises;



import org.example.lab5java.classes.Category;
import org.example.lab5java.classes.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public List<Category> findBySpecificNames(){
        return categoryRepository.findBySpecificNames();
    }


}