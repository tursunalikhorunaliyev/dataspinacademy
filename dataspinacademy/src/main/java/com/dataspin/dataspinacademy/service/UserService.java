package com.dataspin.dataspinacademy.service;

import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.dto.UserInfoDTO;
import com.dataspin.dataspinacademy.entity.UserInfo;
import com.dataspin.dataspinacademy.repository.UserInfoRepository;
import com.dataspin.dataspinacademy.security.JWTGenerator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class UserService {

    private final UserInfoRepository userInfoRepository;
    private final JWTGenerator jwtGenerator;


    public ResponseEntity<ResponseData> createUser(UserInfoDTO userInfoDTO, HttpServletRequest request){
        System.out.println(userInfoDTO.getFirstname());
        System.out.println(userInfoDTO.getLastname());
        System.out.println(userInfoDTO.getMiddlename());
        UserInfo userInfo = new UserInfo();
        userInfo.setFirstname(userInfoDTO.getFirstname());
        userInfo.setLastname(userInfoDTO.getLastname());
        if(userInfoDTO.getMiddlename()!=null){
            userInfo.setMiddlename(userInfoDTO.getMiddlename());
        }
        if(userInfoDTO.getBirthday()!=null){
            final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate localDate = LocalDate.parse(userInfoDTO.getBirthday(), dateTimeFormatter);
            userInfo.setBirthday(localDate);

        }
        userInfo.setPrimaryPhone(userInfoDTO.getTel1());
        if(userInfoDTO.getTel2()!=null){
            userInfo.setSecondaryPhone(userInfoDTO.getTel2());
        }
        if(userInfoDTO.getTg()!=null){
            userInfo.setTelegramUsername(userInfoDTO.getTg());
        }
        userInfo.setUserData(jwtGenerator.getUserFromRequest(request));

        try {
            userInfoRepository.save(userInfo);
        } catch (Exception e) {
            if (e.getMessage().contains("ConstraintViolationException")) {
                return new ResponseEntity<>(new ResponseData(false, "Siz oldin ro'yxatdan o'tgansiz!", null), HttpStatus.BAD_REQUEST);
            }
        }
        return ResponseEntity.ok(new ResponseData(true, "User ma'lumotlari yaratildi", null));
    }
}
