package com.example.web_spring_security_demo.controllers;

import com.example.web_spring_security_demo.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/add")
    public String addCategory(@RequestParam String name){
        return categoryService.addCategory(name);

    }
}
