package com.example.web_spring_security_demo.services;

import com.example.web_spring_security_demo.model.ERole;
import com.example.web_spring_security_demo.model.Role;
import com.example.web_spring_security_demo.model.User;
import com.example.web_spring_security_demo.payload.request.EditUserRequest;
import com.example.web_spring_security_demo.payload.request.SignupRequest;
import com.example.web_spring_security_demo.payload.response.MessageResponse;
import com.example.web_spring_security_demo.payload.response.ResponseAddUserService;
import com.example.web_spring_security_demo.repository.RoleRepository;
import com.example.web_spring_security_demo.repository.UserRepository;
import jakarta.validation.Valid;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.print.attribute.standard.PagesPerMinute;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    RoleRepository roleRepository;
    public Page<User> getAllUsers(Pageable pageable){
        return userRepository.findAll(pageable);
    }

//    public ResponseAddUserService addUser(@Valid @RequestBody SignupRequest signupRequest)
//    {
//        if(userRepository.existsByEmail(signupRequest.getEmail())){
//            return new ResponseAddUserService(false,"Bị trùng email");
//        }
//        if(userRepository.existsByUsername(signupRequest.getUsername())){
//            return new ResponseAddUserService(false,"Bị trùng username");
//        }
//        Set<String> strRoles = signupRequest.getRoles();
//        System.out.println(strRoles);
//        if(strRoles == null)
//        {
//            return new ResponseAddUserService(false,"Chưa cung cấp Role cho User");
//        }else{
//            return new ResponseAddUserService(true,"Thêm User thành công");
//        }
//    }
    public ResponseAddUserService addUser(SignupRequest signUpRequest)
    {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseAddUserService(false,"Error: Username is already in use!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseAddUserService(false,"Error: Email is already in use!");
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElse(null);
            if(userRole ==null){
                return new ResponseAddUserService(false,"Error: Role is not found.");
            }
            roles.add(userRole);
        } else {
            for (String role : strRoles) {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElse(null);
                        if (adminRole == null) {
                            return new ResponseAddUserService(false, "Error: Role admin is not found.");
                        }
                        roles.add(adminRole);
                        break;

                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR).orElse(null);
                        if (modRole == null) {
                            return new ResponseAddUserService(false, "Error: Role mod is not found.");
                        }
                        roles.add(modRole);
                        break;

                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElse(null);
                        if (userRole == null) {
                            return new ResponseAddUserService(false, "Error: Role user is not found.");
                        }
                        roles.add(userRole);
                        break;
                }
            }
        }

        user.setRoles(roles);
        userRepository.save(user);

        return new ResponseAddUserService(true,"Add user successfully");
    }

    public String deleteUserById(Long id){
        if(!userRepository.existsById(id))
        {
            return "Không tìm thấy user có id="+id;
        }
        userRepository.deleteById(id);
        return "Xóa user có id="+id;
    }
    public String editUser(EditUserRequest editUserRequest){
        User user = userRepository.findById(editUserRequest.getId()).orElse(null);
        if(user == null){
            return "Not found user have id="+ editUserRequest.getId();
        }
        user.setEmail(editUserRequest.getEmail());
        user.setPassword(editUserRequest.getPassword());
        userRepository.save(user);
        return "Edit user successfully";
    }
    public String getAllUsersAndExportExcel() throws IOException {
        List<User> userList = userRepository.findAll();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Users");

        // Create a header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Username");
        headerRow.createCell(2).setCellValue("Email");
        headerRow.createCell(3).setCellValue("Password");
        headerRow.createCell(4).setCellValue("Role");
        int row=1;
        for (User user:userList){
            Row contentRow = sheet.createRow(row);
            contentRow.createCell(0).setCellValue(user.getId());
            contentRow.createCell(1).setCellValue(user.getUsername());
            contentRow.createCell(2).setCellValue(user.getEmail());
            contentRow.createCell(3).setCellValue(encoder.encode(user.getPassword()));
            String rolesString = user.getRoles().stream()
                    .map(role -> role.getName().name())
                    .collect(Collectors.joining(","));
            contentRow.createCell(4).setCellValue(rolesString);
            row++;
        }
        try (FileOutputStream fileOut = new FileOutputStream("D:\\thuc_tap\\list_user.xlsx")) {
            workbook.write(fileOut);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Close the workbook
        workbook.close();
        return "List user has been exported to list_user.xlsx";
    }



}






