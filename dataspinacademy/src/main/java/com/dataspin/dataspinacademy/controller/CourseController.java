package com.dataspin.dataspinacademy.controller;

import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.repository.MentorRepository;
import com.dataspin.dataspinacademy.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.swing.plaf.multi.MultiListUI;
import java.io.IOException;

@RestController
@RequestMapping("api/course")
@AllArgsConstructor
public class CourseController {

    private final CourseService courseService;
    @GetMapping("/")
    public ResponseEntity<ResponseData> getAllCourses(){
        return courseService.getAll();
    }
    @GetMapping("/by-status")
    public ResponseEntity<ResponseData> getAllCoursesByStatus(@RequestParam Boolean isActive){
        return courseService.getAllByStatus(isActive);
    }
    @PostMapping("/new")
    public ResponseEntity<ResponseData> create(@RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("forId") Long forId, @RequestParam("typeId") Long typeId, @RequestParam("preview") MultipartFile preview, @RequestParam("status") Boolean status,HttpServletRequest request) throws IOException {
      return courseService.create(name, description, forId, typeId, preview, status, request);
    }
    @GetMapping("/with-prices")
    public ResponseEntity<ResponseData> getWithPrices(){
        return courseService.getWithPrice();
    }
    @GetMapping("/by-type")
    public ResponseEntity<ResponseData> getByTypeId(@RequestParam("id") Long id){
        return courseService.getCoursesByType(id);
    }
    @GetMapping("/by")
    public ResponseEntity<ResponseData> getById(@RequestParam Long id){
        return courseService.courseWithLastPrice(id);
    }



}
