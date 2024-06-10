package com.example.web_spring_security_demo.controllers;

import com.example.web_spring_security_demo.payload.response.ResponseEmail;
import com.example.web_spring_security_demo.services.BorrowedBookService;
import com.example.web_spring_security_demo.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledTasks {
    @Autowired
    private BorrowedBookService borrowedBookService;

    @Autowired
    private EmailService emailService;
//    @Scheduled(cron = "15 * * * * *")
//    public void getTest() {
//        List<ResponseEmail> listOverdueAndNeardueBooks = borrowedBookService.getOverdueAndNearDueBooks();
//
//        for (ResponseEmail responseEmail:listOverdueAndNeardueBooks){
//            StringBuilder content = new StringBuilder();
//            content.append("Các sách đã hết hạn:");
//            content.append(responseEmail.getBookOverdue());
//            content.append("\nCác sách gần hết hạn:");
//            content.append(responseEmail.getBookNearDue());
//            System.out.println(content);
//            String resultSendEmail=emailService.sendMail(responseEmail.getEmailUser(),content.toString(),"Thông báo các sách hết hạn hoặc gần hết hạn");
//        }
//
//    }
}
