package com.example.web_spring_security_demo.controllers;


import com.example.web_spring_security_demo.model.User;
import com.example.web_spring_security_demo.payload.request.LoginRequest;
import com.example.web_spring_security_demo.payload.request.SignupRequest;
import com.example.web_spring_security_demo.payload.response.ResponseAddUserService;
import com.example.web_spring_security_demo.repository.UserRepository;
import com.example.web_spring_security_demo.services.ExcelService;
import com.example.web_spring_security_demo.services.UserService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import static org.springframework.transaction.support.TransactionSynchronization.STATUS_ROLLED_BACK;

@RestController
@RequestMapping("/api/excel")
public class ExcelController {

    @Autowired
    private ExcelService excelService;
    @Autowired
    private UserService userService;

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    @Transactional
    public ResponseEntity<String> uploadAndSaveExcel(@RequestParam("file") MultipartFile file) throws IOException {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        int count = 0;
        Set<String> setEmail = new HashSet<>();
        Set<String> setUsername = new HashSet<>();
        boolean isSuccessAll = true;
        List<SignupRequest> signupRequests = new ArrayList<>();

        for (Row row : sheet) {
            if (count == 0) {
                count++;
                continue;
            }

            String username = row.getCell(0) != null ? row.getCell(0).getStringCellValue() : "";
            String email = row.getCell(1) != null ? row.getCell(1).getStringCellValue() : "";
            String password = row.getCell(2) != null ? String.valueOf((int) row.getCell(2).getNumericCellValue()) : "";
            String roles = row.getCell(3) != null ? row.getCell(3).getStringCellValue() : "";

            if (Objects.equals(username, "") && Objects.equals(email, "") && Objects.equals(roles, "")) {
                System.out.println("Đã vào");
                break;
            } else if (Objects.equals(username, "")) {
                Cell cellNotice = row.createCell(4);
                cellNotice.setCellValue("Vui lòng nhập username");
            } else if (Objects.equals(roles, "")) {
                Cell cellNotice = row.createCell(4);
                cellNotice.setCellValue("Vui lòng nhập roles");
            } else if (Objects.equals(email, "")) {
                Cell cellNotice = row.createCell(4);
                cellNotice.setCellValue("Vui lòng nhập email");
            } else if (password.isEmpty()) {
                Cell cellNotice = row.createCell(4);
                cellNotice.setCellValue("Vui lòng nhập Pass");
            } else {
                boolean checkAddUsername = setUsername.add(username);
                boolean checkAddEmail = setEmail.add(email);
                if (!checkAddEmail && !checkAddUsername) {
                    Cell cellNotice = row.createCell(4);
                    cellNotice.setCellValue("Vui lòng nhập Username và Email");
                } else if (!checkAddEmail) {
                    Cell cellNotice = row.createCell(4);
                    cellNotice.setCellValue("Vui lòng nhập Email");
                } else if (!checkAddUsername) {
                    Cell cellNotice = row.createCell(4);
                    cellNotice.setCellValue("Vui lòng nhập Username");
                } else {
                    SignupRequest tmp = new SignupRequest(username, email, new HashSet<>(List.of(roles.split(","))), password);
                    signupRequests.add(tmp);
                    ResponseAddUserService responseAddUserService = userService.addUser(tmp);
                    if (!responseAddUserService.isAddSuccess()) {
                        isSuccessAll = false;
                        Cell cellNotice = row.createCell(4);
                        cellNotice.setCellValue(responseAddUserService.getMessage());
                    }
                    // Assuming you want to log the message somewhere

                    System.out.println(responseAddUserService.getMessage());
                }
            }
        }



        try (FileOutputStream fileOut = new FileOutputStream(new File("D://thuc_tap//ketqua_chuan.xlsx"))) {
            workbook.write(fileOut);
        }
        workbook.close();

        if (!isSuccessAll) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCompletion(int status) {
                    if (status == STATUS_ROLLED_BACK) {
                        throw new RuntimeException("Transaction is being rolled back.");
                    }
                }
            });
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: One or more users could not be added. Transaction is being rolled back.");
        }

        return ResponseEntity.ok("File has been modified and saved to D://thuc_tap//ketqua_chuan.xlsx");
    }


}






