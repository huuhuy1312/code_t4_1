package com.example.web_spring_security_demo.controllers;

import com.example.web_spring_security_demo.model.ERole;
import com.example.web_spring_security_demo.model.Role;
import com.example.web_spring_security_demo.model.User;
import com.example.web_spring_security_demo.payload.request.LoginRequest;
import com.example.web_spring_security_demo.payload.request.SignupRequest;
import com.example.web_spring_security_demo.payload.response.JwtResponse;
import com.example.web_spring_security_demo.payload.response.MessageResponse;
import com.example.web_spring_security_demo.repository.RoleRepository;
import com.example.web_spring_security_demo.repository.UserRepository;
import com.example.web_spring_security_demo.security.jwt.JwtUtils;
import com.example.web_spring_security_demo.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class Authcontroller {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    PasswordEncoder encoder;
    @GetMapping("/test")
    public String test()
    {
        return "abc";
    }
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest)
    {
        System.out.println(loginRequest.getPassword());
        System.out.println(loginRequest.getUsername());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Authentication authentication1 =SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication1);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

}
