package com.dataspin.dataspinacademy.controller;

import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.entity.CourseAboutPart;
import com.dataspin.dataspinacademy.service.CourseAboutPartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("api/course-about-parts")
@AllArgsConstructor
public class CourseAboutPartController {

    private final CourseAboutPartService courseAboutPartService;

    @PostMapping("/new")
    public ResponseEntity<ResponseData> create(@RequestParam("title") String title, @RequestParam("description") String desc, @RequestParam("icon")MultipartFile icon, HttpServletRequest request) throws IOException {
        return  courseAboutPartService.create(icon, title, desc, request);
    }
    @GetMapping("/")
    public ResponseEntity<ResponseData> getAll(HttpServletRequest request){
        return  courseAboutPartService.getAll(request);
    }
}
