package com.dataspin.dataspinacademy.controller;

import com.dataspin.dataspinacademy.dto.EmployeeDTO;
import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("api/employee")
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    @PostMapping("/new")
    public ResponseEntity<ResponseData> create(@ModelAttribute @Valid EmployeeDTO employeeDTO, HttpServletRequest request) throws IOException {
        return employeeService.create(employeeDTO, request);
    }
    @GetMapping("/")
    public ResponseEntity<ResponseData> getAll(){
        return employeeService.getAll();
    }
    @GetMapping("/teachers")
    public ResponseEntity<ResponseData> getAllTeachers(){
        return employeeService.getAllTeachers();
    }
    @PostMapping("/update-profile-photo")
    public ResponseEntity<ResponseData> updateProfilePhoto(@RequestParam("employee_id") Long employeeId, @RequestParam(value = "photo_id", required = false) Long photoId,  @RequestParam(value = "photo", required = false)MultipartFile photo, HttpServletRequest request) throws IOException {
        return employeeService.updateEmployeeImage(employeeId, photoId, photo,  request);
    }

}
