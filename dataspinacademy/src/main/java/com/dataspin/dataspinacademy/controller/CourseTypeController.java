package com.dataspin.dataspinacademy.controller;

import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.service.CourseTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("api/course-type")
@AllArgsConstructor
public class CourseTypeController {
    private final CourseTypeService courseTypeService;
    @GetMapping("/")
    public ResponseEntity<ResponseData> getAll(){
        return courseTypeService.getAll();
    }
    @PostMapping("/new")
    public ResponseEntity<ResponseData> create(@RequestParam("name") String name, @RequestParam("desc") String desc, @RequestParam("tags") String tags, @RequestParam("photo") MultipartFile photo, HttpServletRequest request) throws IOException {
        return courseTypeService.create(name, desc, tags, photo, request);
    }
}
