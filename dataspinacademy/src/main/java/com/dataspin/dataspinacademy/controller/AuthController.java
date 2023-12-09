package com.dataspin.dataspinacademy.controller;

import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    AuthService authService;
    @PostMapping("/send-code")
    public ResponseEntity<ResponseData> sendCode(@RequestParam("phone") String phone){
       return authService.sendCode(phone);
    }

    @PostMapping("/check-code")
    public  ResponseEntity<ResponseData> checkCode(@RequestParam("phone") String phone,@RequestParam("code") String code){
        return  authService.checkCode(phone, code);
    }



}
