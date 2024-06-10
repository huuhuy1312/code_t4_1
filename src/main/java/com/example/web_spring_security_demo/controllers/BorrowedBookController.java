package com.example.web_spring_security_demo.controllers;

import com.example.web_spring_security_demo.payload.request.AddBorrowedBookRequest;
import com.example.web_spring_security_demo.payload.response.ResponseEmail;
import com.example.web_spring_security_demo.services.BorrowedBookService;
import com.example.web_spring_security_demo.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrowed-book")

public class BorrowedBookController {
    @Autowired
    private BorrowedBookService borrowedBookService;

    @Autowired
    private EmailService emailService;
    @PostMapping("/add")
    public String addBorrowedBook(@RequestBody AddBorrowedBookRequest addBorrowedBookRequest)
    {
        return borrowedBookService.addBorrowedBook(addBorrowedBookRequest);
    }

//    @Scheduled(cron = "15 * * * * *")
//    public void sendEmailNoticeBorrowedBookLate(){
//
//    }


}

