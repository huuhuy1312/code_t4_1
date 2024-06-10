package com.example.web_spring_security_demo.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEmail {
    private String emailUser;
    private String bookOverdue;
    private String bookNearDue;
}
