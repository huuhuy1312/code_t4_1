package com.example.web_spring_security_demo.controllers;

import com.example.web_spring_security_demo.model.User;
import com.example.web_spring_security_demo.payload.request.EditUserRequest;
import com.example.web_spring_security_demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<User> getUsers(Pageable pageable){
        return userService.getAllUsers(pageable);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteUserByid(@PathVariable  Long id){
        String result = userService.deleteUserById(id);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/edit")
    public String editUser(@RequestBody EditUserRequest editUserRequest){
        return userService.editUser(editUserRequest);
    }

    @GetMapping("/excel")
    public String exportListUserToExcel() throws IOException {
        return userService.getAllUsersAndExportExcel();
    }

}
