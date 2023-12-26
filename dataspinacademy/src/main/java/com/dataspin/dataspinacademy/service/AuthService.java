package com.dataspin.dataspinacademy.service;

import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.dto.TokenData;
import com.dataspin.dataspinacademy.entity.*;
import com.dataspin.dataspinacademy.repository.PhoneCodeRepository;
import com.dataspin.dataspinacademy.repository.RoleRepository;
import com.dataspin.dataspinacademy.repository.UserDataRepository;
import com.dataspin.dataspinacademy.repository.UserInfoRepository;
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
import java.util.Collections;
import java.util.Random;

@Service
@AllArgsConstructor
public class AuthService {
    AuthenticationManager authenticationManager;
   JWTGenerator jwtGenerator;
   PhoneCodeRepository phoneCodeRepository;
   UserDataRepository userDataRepository;
   PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserInfoRepository userInfoRepository;



   public ResponseEntity<ResponseData> tokenSession(String phone, String code){

       if(phoneCodeRepository.existsByPhoneAndCode(phone, code)){
           final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(phone, phone));
           SecurityContextHolder.getContext().setAuthentication(authentication);
           final String token = jwtGenerator.generateToken(authentication);
           UserData userData = userDataRepository.findByPhone(jwtGenerator.getUsernameFromToken(token)).orElse(null);
           boolean existsUserInfo = userInfoRepository.existsByUserData(userData);
           return new ResponseEntity<>(new ResponseData(true, existsUserInfo?"Active":"Inactive", new TokenData("Foydalanuvchi tizimga kirdi", token)), HttpStatus.OK);
       }else{
           return new ResponseEntity<>(new ResponseData(false, "Kod xato kiritildi", null), HttpStatus.BAD_REQUEST);
       }

   }
   public ResponseEntity<ResponseData> sendCode(String phone){
       Random random = new Random();
       int randomInt = random.nextInt(100000, 999999);
       while (phoneCodeRepository.findByCode(String.valueOf(randomInt)).isPresent()){
           randomInt = random.nextInt(100000, 999999);
       }
       PhoneCode phoneCode = new PhoneCode();
       phoneCode.setPhone(phone);
       phoneCode.setCode(String.valueOf(randomInt));

       phoneCodeRepository.save(phoneCode);
       return ResponseEntity.ok(new ResponseData(true, "Sms jo'natildi", String.valueOf(randomInt)));
   }
   public ResponseEntity<ResponseData> checkCodeAdmin(String phone, String code){
        if(phoneCodeRepository.existsByPhoneAndCode(phone, code)){
            if(userDataRepository.existsByPhone(phone)){
                return ResponseEntity.ok(new ResponseData(true, "Success!", code));
            }
            else{

                final UserData user = new UserData();
                Role role = roleRepository.findByName("ADMIN").orElse(null);
                if (role == null) {
                    final Role forSaveRole = new Role();
                    forSaveRole.setName("ADMIN");
                    role = roleRepository.save(forSaveRole);
                }
                user.setPhone(phone);
                user.setPass(passwordEncoder.encode(phone));
                user.setRoles(Collections.singleton(role));
                userDataRepository.save(user);
                return ResponseEntity.ok(new ResponseData(true, "Success!", code));
            }
        }
        else{
            return new ResponseEntity<>(new ResponseData(false, "Failed", null), HttpStatus.BAD_REQUEST);
        }

   }
    public ResponseEntity<ResponseData> checkCodeUser(String phone, String code){
        if(phoneCodeRepository.existsByPhoneAndCode(phone, code)){
            if(userDataRepository.existsByPhone(phone)){
                return ResponseEntity.ok(new ResponseData(true, "Success!", code));
            }
            else{

                final UserData user = new UserData();
                Role role = roleRepository.findByName("USER").orElse(null);
                if (role == null) {
                    final Role forSaveRole = new Role();
                    forSaveRole.setName("USER");
                    role = roleRepository.save(forSaveRole);
                }
                user.setPhone(phone);
                user.setPass(passwordEncoder.encode(phone));
                user.setRoles(Collections.singleton(role));
                userDataRepository.save(user);
                return ResponseEntity.ok(new ResponseData(true, "Success!", code));
            }
        }
        else{
            return new ResponseEntity<>(new ResponseData(false, "Failed", null), HttpStatus.BAD_REQUEST);
        }
    }

}
