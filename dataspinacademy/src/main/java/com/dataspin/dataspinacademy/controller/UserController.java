package com.dataspin.dataspinacademy.controller;

import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.dto.UserInfoDTO;
import com.dataspin.dataspinacademy.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("api/user")
@AllArgsConstructor
public class UserController {


    private final UserService userService;
    @PostMapping("/create")
    public ResponseEntity<ResponseData> createUser(@RequestBody @Valid UserInfoDTO userInfo, HttpServletRequest request){
        System.out.println(userInfo.getFirstname());
       return userService.createUser(userInfo, request);
    }
}
