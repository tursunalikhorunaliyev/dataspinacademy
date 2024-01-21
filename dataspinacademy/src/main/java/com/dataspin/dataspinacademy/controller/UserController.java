package com.dataspin.dataspinacademy.controller;

import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.dto.UpdateProfileDTO;
import com.dataspin.dataspinacademy.dto.UserInfoDTO;
import com.dataspin.dataspinacademy.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("api/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<ResponseData> createUser(@RequestBody @Valid UserInfoDTO userInfo, HttpServletRequest request) throws IOException {
        return userService.createUser(userInfo, request);
    }

    @GetMapping("/")
    public ResponseEntity<ResponseData> getAllUserInfo(HttpServletRequest request) {
        return userService.getAllUser(request);
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseData> getMe(HttpServletRequest request) {
        return userService.getMe(request);
    }

    @PostMapping("/profile-photo")
    public ResponseEntity<ResponseData> updateProfilePhoto(@RequestParam("photo") MultipartFile photo, HttpServletRequest request) throws IOException {
        return userService.changeProfilePhoto(photo, request);
    }
    @PostMapping("/update-profile")
    public ResponseEntity<ResponseData> updateProfile(@ModelAttribute @Valid UpdateProfileDTO updateProfileDTO, HttpServletRequest request) throws IOException {
        return userService.updateProfile(updateProfileDTO, request);
    }

}
