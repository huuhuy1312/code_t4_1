package com.example.web_spring_security_demo.controllers;

import com.example.web_spring_security_demo.payload.request.AddBookRequest;
import com.example.web_spring_security_demo.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/book")
public class BookController {
    @Autowired
    private BookService bookService;
    @PostMapping("/add")
    public String addBook(@RequestBody AddBookRequest addBookRequest)
    {
        return  bookService.addBook(addBookRequest);
    }
}
