package com.dataspin.dataspinacademy.controller;

import com.dataspin.dataspinacademy.dto.FileUploadDTO;
import com.dataspin.dataspinacademy.dto.OnlyNameRequest;
import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
@RestController
@RequestMapping("api/tag")
@AllArgsConstructor
public class TagController {

    private final BaseService baseService;
    @PostMapping("/create")
    public ResponseEntity<ResponseData> create(@RequestBody @Valid OnlyNameRequest tag, HttpServletRequest httpServletRequest){
        return baseService.create(tag, httpServletRequest, this);
    }
    @PostMapping("/upload")
    public ResponseEntity<ResponseData> upload(@RequestBody FileUploadDTO file, HttpServletRequest request){
       return baseService.fromExcel(file, request, this);
    }
    @GetMapping("/")
    public ResponseEntity<ResponseData> all(){
        return baseService.getAllTags();
    }
}
