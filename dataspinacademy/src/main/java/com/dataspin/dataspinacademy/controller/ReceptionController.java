package com.dataspin.dataspinacademy.controller;

import com.dataspin.dataspinacademy.dto.ReceptionDTO;
import com.dataspin.dataspinacademy.dto.ReceptionFilterDTO;
import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.service.ReceptionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;

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
    public ResponseEntity<ResponseData> getAllByAdmin(@RequestParam(required = false) String course_name, @RequestParam(required = false) String start_date, @RequestParam(required = false) String end_date, HttpServletRequest request) throws ParseException {
        return receptionService.getAllByAdmin(course_name, start_date, end_date ,request);
    }
}
