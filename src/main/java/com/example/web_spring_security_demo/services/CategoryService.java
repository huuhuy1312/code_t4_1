package com.example.web_spring_security_demo.services;

import com.example.web_spring_security_demo.model.Category;
import com.example.web_spring_security_demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public String addCategory(String name){
        Category category = new Category();
        category.setName(name);
        categoryRepository.save(category);
        return "Thêm category thành công";
    }
}
