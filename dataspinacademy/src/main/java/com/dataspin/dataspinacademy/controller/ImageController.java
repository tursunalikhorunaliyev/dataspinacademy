package com.dataspin.dataspinacademy.controller;


import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.entity.UserData;
import com.dataspin.dataspinacademy.security.JWTGenerator;
import com.dataspin.dataspinacademy.service.ImageDataService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("api/image")
@AllArgsConstructor
public class ImageController {
    private final ImageDataService imageDataService;
    @GetMapping("/")
    public ResponseEntity<byte[]> getPhoto(@RequestParam Long id){
        return imageDataService.getImage(id);
    }
    @GetMapping("/all")
    public ResponseEntity<ResponseData> allPhoto(HttpServletRequest request){
       return imageDataService.getAll(request);
    }
    @PostMapping("/upload")
    public ResponseEntity<ResponseData> upload(@RequestParam("photo") MultipartFile photo, HttpServletRequest request) throws IOException {
        return imageDataService.uploadPicture(photo, request);
    }
}
