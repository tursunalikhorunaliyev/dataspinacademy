package com.dataspin.dataspinacademy.controller;

import com.dataspin.dataspinacademy.dto.CoursePriceDTO;
import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.service.CoursePriceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("api/course-price")
@AllArgsConstructor
public class CoursePriceController {

    private final CoursePriceService coursePriceService;

    @PostMapping("/new")
    public ResponseEntity<ResponseData> create( @RequestBody @Valid CoursePriceDTO coursePriceDTO, HttpServletRequest request){
        return coursePriceService.create(coursePriceDTO, request);
    }
    @GetMapping("/")
    public ResponseEntity<ResponseData> getAll(){
        return coursePriceService.getAll();
    }

    @GetMapping("/by-course")
    public ResponseEntity<ResponseData> getByCourse(@RequestParam Long courseId){
        return coursePriceService.getAllByCourse(courseId);
    }
}
