package com.dataspin.dataspinacademy.controller;


import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.service.ImageDataService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/image")
@AllArgsConstructor
public class ImageController {
    private final ImageDataService imageDataService;
    @GetMapping("/")
    public ResponseEntity<byte[]> getPhoto(@RequestParam Long id){
        return imageDataService.getImage(id);
    }
}
