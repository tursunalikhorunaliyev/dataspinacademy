package com.dataspin.dataspinacademy.controller;

import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.dto.UserInfoDTO;
import com.dataspin.dataspinacademy.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    @PostMapping("/register/user")
    public ResponseEntity<ResponseData> registerUser(@ModelAttribute @Valid UserInfoDTO dto) throws IOException {
        return  authService.register(dto, false);
    }
    @PostMapping("/register/nimda")
    public ResponseEntity<ResponseData> registerNimda(@ModelAttribute @Valid UserInfoDTO dto) throws IOException {
        return  authService.register(dto, true);
    }
    @PostMapping("/login")
    public ResponseEntity<ResponseData> login(@RequestParam("username") String username, @RequestParam("password") String password){
        return authService.loginUser(username, password);
    }
}
