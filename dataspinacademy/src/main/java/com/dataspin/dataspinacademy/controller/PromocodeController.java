package com.dataspin.dataspinacademy.controller;

import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.service.PromocodeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/promo")
@AllArgsConstructor
public class PromocodeController {

    private final PromocodeService service;
    @PostMapping("/new")
    public ResponseEntity<ResponseData> create(@RequestParam("promocode") String promoCode, HttpServletRequest request){
        return service.create(promoCode, request);
    }
    @GetMapping("/")
    public ResponseEntity<ResponseData> all(){
        return service.allPromocode();
    }
    @GetMapping("/user")
    public ResponseEntity<ResponseData> userPromocodes(HttpServletRequest request){
        return service.getUserPromocodes(request);
    }

    @GetMapping("/subscribers")
    public ResponseEntity<ResponseData> userPromoSubscribers(@RequestParam Long id, HttpServletRequest request){
        return service.getUserPromocodeSubscribers(id, request);
    }
    @PostMapping("/status")
    public ResponseEntity<ResponseData> changeStatsu(@RequestParam("id") Long id){
        return service.changeStatus(id);

    }
    @PostMapping("/active-count")
    public ResponseEntity<ResponseData> changeActiveCount(@RequestParam("id") Long id, @RequestParam("count") Integer count){
        return service.changeActiveCount(id, count);
    }

}
