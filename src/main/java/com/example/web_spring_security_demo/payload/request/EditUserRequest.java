package com.example.web_spring_security_demo.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EditUserRequest {
    private Long id;
    private String password;
    private String email;
}
