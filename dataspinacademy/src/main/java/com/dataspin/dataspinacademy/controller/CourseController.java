package com.dataspin.dataspinacademy.controller;

import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.service.CourseService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.collections4.queue.PredicatedQueue;
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
    @PostMapping("/new")
    public ResponseEntity<ResponseData> create(@RequestParam("name") String name, @RequestParam("forId") Long forid, @RequestParam("typeId") Long typeId, @RequestParam("preview") MultipartFile preview, HttpServletRequest request) throws IOException {
      return courseService.create(name, forid, typeId, preview, request);
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
