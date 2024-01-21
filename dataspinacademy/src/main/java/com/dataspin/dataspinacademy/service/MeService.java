package com.dataspin.dataspinacademy.service;

import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.repository.UserInfoRepository;
import com.dataspin.dataspinacademy.security.JWTGenerator;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Service
@AllArgsConstructor
public class MeService {

    private final UserInfoRepository userInfoRepository;
    private final JWTGenerator jwtGenerator;

    public ResponseEntity<ResponseData> getMe(HttpServletRequest request){
        return ResponseEntity.ok(new ResponseData(true, "Me", userInfoRepository.findByUserData_Id(jwtGenerator.getUserFromRequest(request).getId())));
    }

}
