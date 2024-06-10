package com.example.web_spring_security_demo.repository;

import com.example.web_spring_security_demo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}
