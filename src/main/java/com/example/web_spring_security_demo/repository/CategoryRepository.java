package com.example.web_spring_security_demo.repository;

import com.example.web_spring_security_demo.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
