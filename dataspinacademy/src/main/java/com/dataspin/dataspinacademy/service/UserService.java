package com.dataspin.dataspinacademy.service;

import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.dto.UserInfoDTO;
import com.dataspin.dataspinacademy.entity.ImageData;
import com.dataspin.dataspinacademy.entity.UserData;
import com.dataspin.dataspinacademy.entity.UserInfo;
import com.dataspin.dataspinacademy.repository.ImageDataRepository;
import com.dataspin.dataspinacademy.repository.UserInfoRepository;
import com.dataspin.dataspinacademy.security.JWTGenerator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class UserService {

    private final UserInfoRepository userInfoRepository;
    private final JWTGenerator jwtGenerator;
    private final ImageDataRepository imageDataRepository;
    private  final  AuthService authService;


    public ResponseEntity<ResponseData> createUser(UserInfoDTO userInfoDTO, HttpServletRequest request) throws IOException {

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
        UserData userData = jwtGenerator.getUserFromRequest(request);
        userInfo.setUserData(userData);
        if(userInfoDTO.getProfilePhoto()!=null){
            ImageData imageData = new ImageData();
            imageData.setFilename(userInfoDTO.getProfilePhoto().getOriginalFilename());
            imageData.setContent(userInfoDTO.getProfilePhoto().getBytes());
            imageData.setUser(userData);
            userInfo.setProfilePhoto(imageData);
        }

        try {
            userInfoRepository.save(userInfo);
        } catch (Exception e) {
            if (e.getMessage().contains("ConstraintViolationException")) {
                return new ResponseEntity<>(new ResponseData(false, "Siz oldin ro'yxatdan o'tgansiz!", null), HttpStatus.BAD_REQUEST);
            }
        }
        return ResponseEntity.ok(new ResponseData(true, "User ma'lumotlari yaratildi", null));
    }
    public ResponseEntity<ResponseData> changeProfilePhoto(MultipartFile photo, HttpServletRequest request) throws IOException {
        UserData userData = jwtGenerator.getUserFromRequest(request);
        UserInfo userInfo = userInfoRepository.findByUserData(userData).orElse(null);
        if(userInfo==null){
            return new ResponseEntity<>(new ResponseData(false, "User topilmadi", null),  HttpStatus.BAD_REQUEST);
        }
        else if(userInfo.getProfilePhoto()==null){
            ImageData imageData = new ImageData();
            imageData.setFilename(photo.getOriginalFilename());
            imageData.setContent(photo.getBytes());
            imageData.setUser(userData);
            userInfo.setProfilePhoto(imageData);
            userInfoRepository.save(userInfo);
            return ResponseEntity.ok(new ResponseData(true, "Ma'lumotlar saqlandi", null));
        }
        else{
            ImageData imageData = new ImageData();
            imageData.setFilename(photo.getOriginalFilename());
            imageData.setContent(photo.getBytes());
            imageData.setUser(userData);
            userInfo.setProfilePhoto(imageData);
            userInfoRepository.save(userInfo);
            imageDataRepository.delete(userInfo.getProfilePhoto());
            return ResponseEntity.ok(new ResponseData(true, "Ma'lumotlar saqlandi", null));
        }
    }
    public ResponseEntity<ResponseData> getAllUser(HttpServletRequest request){
        if(!jwtGenerator.isAdmin(jwtGenerator.getUserFromRequest(request))){
            return new ResponseEntity<>(new ResponseData(false, "Ushbu resursga murojaat qilish huquqingiz mavjud emas", null), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new ResponseData(true, "Success!", userInfoRepository.findAllInfo()));
    }
}
