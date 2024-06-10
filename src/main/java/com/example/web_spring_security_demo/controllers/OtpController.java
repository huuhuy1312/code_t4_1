//package com.example.web_spring_security_demo.controllers;
//
//import com.example.web_spring_security_demo.payload.request.OtpRequest;
//import com.example.web_spring_security_demo.payload.request.OtpValidationRequest;
//import com.example.web_spring_security_demo.payload.response.OtpResponseDto;
//import com.example.web_spring_security_demo.services.SmsService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/otp")
//@Slf4j
//public class OtpController {
//
//    @Autowired
//    private SmsService smsService;
//
//    @GetMapping("/process")
//    public String processSMS() {
//        return "SMS sent";
//    }
//
//    @PostMapping("/send-otp")
//    public OtpResponseDto sendOtp(@RequestBody OtpRequest otpRequest) {
//        log.info("inside sendOtp :: "+otpRequest.getUsername());
//        return smsService.sendSMS(otpRequest);
//    }
//    @PostMapping("/validate-otp")
//    public String validateOtp(@RequestBody OtpValidationRequest otpValidationRequest) {
//        log.info("inside validateOtp :: "+otpValidationRequest.getUsername()+" "+otpValidationRequest.getOtpNumber());
//        return smsService.validateOtp(otpValidationRequest);
//    }
//
//}
