package com.dataspin.dataspinacademy.service;

import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.dto.TokenData;
import com.dataspin.dataspinacademy.dto.UserInfoDTO;
import com.dataspin.dataspinacademy.entity.*;
import com.dataspin.dataspinacademy.repository.*;
import com.dataspin.dataspinacademy.security.AuthExceptionEntryPoint;
import com.dataspin.dataspinacademy.security.JWTGenerator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Random;

@Service
@AllArgsConstructor
public class AuthService {
    AuthenticationManager authenticationManager;
   JWTGenerator jwtGenerator;
   UserDataRepository userDataRepository;
   PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserInfoRepository userInfoRepository;
    private final ImageDataRepository imageDataRepository;


    public ResponseEntity<ResponseData> loginUser(String username, String password){

            final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            if(authentication.isAuthenticated()){
                SecurityContextHolder.getContext().setAuthentication(authentication);
                final String token = jwtGenerator.generateToken(authentication);
                return new ResponseEntity<>(new ResponseData(true, "Foydalanuvchi tizimga kirdi", token), HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(new ResponseData(false, "Foydalanuvchi tizimga kira olmadi", null), HttpStatus.UNAUTHORIZED);
            }

        

    }
    public ResponseEntity<ResponseData> register(UserInfoDTO dto, boolean isAdmin) throws IOException {
        if (userDataRepository.existsByUsername(dto.getUsername())) {
            return new ResponseEntity<>(new ResponseData(false, "Bu foydalanuvchi mavjud!", null), HttpStatus.BAD_REQUEST);
        }

        UserData user = new UserData();
        Role role = roleRepository.findByName(isAdmin?"ADMIN":"USER").orElse(null);
        if (role == null) {
            final Role forSaveRole = new Role();
            forSaveRole.setName(isAdmin?"ADMIN":"USER");
            role = roleRepository.save(forSaveRole);
        }

        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(Collections.singleton(role));

        user = userDataRepository.save(user);

        UserInfo userInfo = new UserInfo();
        userInfo.setFirstname(dto.getFirstname());
        userInfo.setLastname(dto.getLastname());
        userInfo.setMiddlename(dto.getMiddlename());

        if(dto.getProfilePhoto()!=null){
            ImageData imageData = new ImageData();
            imageData.setFilename(dto.getProfilePhoto().getOriginalFilename());
            imageData.setContent(dto.getProfilePhoto().getBytes());
            imageData.setUser(user);
            userInfo.setProfilePhoto(imageData);
        }

        if(dto.getBirthday()!=null){
            final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate localDate = LocalDate.parse(dto.getBirthday(), dateTimeFormatter);
            userInfo.setBirthday(localDate);

        }
        userInfo.setPrimaryPhone(dto.getTel1());
        userInfo.setSecondaryPhone(dto.getTel2());
        userInfo.setUserData(user);

        try {
            userInfoRepository.save(userInfo);
        } catch (Exception e) {
            if (e.getMessage().contains("ConstraintViolationException")) {
                return new ResponseEntity<>(new ResponseData(false, "Siz oldin ro'yxatdan o'tgansiz!", null), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(new ResponseData(true,  isAdmin?"Admin ro'yhatdan o'tdi!":"User ro'yxatdan o'tdi", null), HttpStatus.OK);
    }


}
