package com.example.web_spring_security_demo.repository;

import com.example.web_spring_security_demo.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
