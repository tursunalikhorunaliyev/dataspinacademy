package com.dataspin.dataspinacademy.controller;

import com.dataspin.dataspinacademy.dto.OnlyNameRequest;
import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("api/stuff")
@AllArgsConstructor
public class StuffController {

    private final BaseService baseService;

    @PostMapping("/create")
    public ResponseEntity<ResponseData> create(@RequestBody @Valid OnlyNameRequest stuff, HttpServletRequest request){
        return baseService.create(stuff, request, this);
    }
    @GetMapping("/")
    public ResponseEntity<ResponseData> getAll(){
        return baseService.getAllStuff();
    }
}
