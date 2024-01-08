package com.dataspin.dataspinacademy.controller;

import com.dataspin.dataspinacademy.dto.AboutUsDTO;
import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.service.AboutUsService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("api/about-us")
@AllArgsConstructor
public class AboutUsController {

    private final AboutUsService aboutUsService;
    @PostMapping("/create")
    public ResponseEntity<ResponseData> create(@ModelAttribute @Valid AboutUsDTO aboutUsDTO, HttpServletRequest request) throws IOException {
        return aboutUsService.create(aboutUsDTO, request);
    }
    @PostMapping("/additional-photo/add")
    public ResponseEntity<ResponseData> addAdditionalPhoto(@RequestParam("photo") MultipartFile photo, HttpServletRequest request) throws IOException {
        return aboutUsService.addAdditionalPhoto(photo, request);
    }

    @PostMapping("/license-photo/add")
    public ResponseEntity<ResponseData> addLicensePhoto(@RequestParam("photo") MultipartFile photo, HttpServletRequest request) throws IOException {
        return aboutUsService.addLicensePhoto(photo, request);
    }

    @GetMapping("/")
    public ResponseEntity<ResponseData> getAllInformation(){
        return aboutUsService.getAllInformation();
    }
    @PostMapping("/youtube")
    public ResponseEntity<ResponseData> addYouTubeLinks(@RequestParam("links") String links){
        return aboutUsService.changeYouTubeLinks(links);
    }
}
