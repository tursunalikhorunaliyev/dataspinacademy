package com.dataspin.dataspinacademy.controller;

import com.dataspin.dataspinacademy.dto.ReceptionDTO;
import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.service.ReceptionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("api/reception")
@AllArgsConstructor
public class ReceptionController {
    private final ReceptionService receptionService;
    @PostMapping("/new")
    public ResponseEntity<ResponseData> create(@RequestBody @Valid ReceptionDTO receptionDTO, HttpServletRequest request){
       return receptionService.create(receptionDTO, request);
    }
    @GetMapping("/by-user")
    public ResponseEntity<ResponseData> getAllByUser(HttpServletRequest request){
        return receptionService.getAllByUser(request);
    }
    @GetMapping("/by-admin")
    public ResponseEntity<ResponseData> getAllByAdmin(HttpServletRequest request){
        return receptionService.getAllByAdmin(request);
    }
}
