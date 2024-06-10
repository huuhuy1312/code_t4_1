package com.example.web_spring_security_demo.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class JwtResponse {
    private String token;
    private final String type ="Bearer";
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
}
