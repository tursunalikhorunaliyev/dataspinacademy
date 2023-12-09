package com.dataspin.dataspinacademy.controller;

import com.dataspin.dataspinacademy.dto.PhysicalFaceDTO;
import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.service.PhysicalFaceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("api/face")
@AllArgsConstructor
public class PhysicalFaceController {
    private final PhysicalFaceService physicalFaceService;

    @PostMapping("/new")
    public ResponseEntity<ResponseData> create(@RequestBody @Valid PhysicalFaceDTO face, HttpServletRequest request){
        return physicalFaceService.create(face, request);
    }
    @GetMapping("/")
    public ResponseEntity<ResponseData> getAll(){
        return physicalFaceService.getAll();
    }
}
