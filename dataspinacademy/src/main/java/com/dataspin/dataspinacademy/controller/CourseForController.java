package com.dataspin.dataspinacademy.controller;

import com.dataspin.dataspinacademy.dto.CourseForDTO;
import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.service.CourseForService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("api/course-for")
@AllArgsConstructor
public class CourseForController {
    private final CourseForService courseForService;

    @PostMapping("/create")
    public ResponseEntity<ResponseData> create(@RequestBody @Valid CourseForDTO courseForDTO, HttpServletRequest request){
        return courseForService.create(courseForDTO, request);
    }

    @GetMapping("/")
    public ResponseEntity<ResponseData> getAll(){
        return courseForService.getAll();
    }
}
