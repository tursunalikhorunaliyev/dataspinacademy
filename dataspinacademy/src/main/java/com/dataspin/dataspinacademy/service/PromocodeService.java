package com.dataspin.dataspinacademy.service;

import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.entity.Promocode;
import com.dataspin.dataspinacademy.entity.UserData;
import com.dataspin.dataspinacademy.entity.UserInfo;
import com.dataspin.dataspinacademy.projection.ImageDataInfo;
import com.dataspin.dataspinacademy.projection.PromocodeSubscribersInfo;
import com.dataspin.dataspinacademy.projection.UserInfoInfo;
import com.dataspin.dataspinacademy.repository.PromocodeRepository;
import com.dataspin.dataspinacademy.repository.UserInfoRepository;
import com.dataspin.dataspinacademy.security.JWTGenerator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PromocodeService {

    private final JWTGenerator jwtGenerator;
    private final PromocodeRepository promocodeRepository;
    private final UserInfoRepository userInfoRepository;

    public ResponseEntity<ResponseData> create(String promoCode, HttpServletRequest request){
        UserData userData = jwtGenerator.getUserFromRequest(request);
        UserInfo userInfo = userInfoRepository.findByUserData(userData).get();
        Optional<Promocode> promocode = promocodeRepository.findByPromoCode(promoCode);
        if(promocode.isPresent()){
            return new ResponseEntity<>(new ResponseData(false, "Bu PROMOKOD oldin yaratilgan", null), HttpStatus.BAD_REQUEST);
        }
        Promocode promocodeNew = new Promocode();
        promocodeNew.setPromoCode(promoCode);
        promocodeNew.setTotalCount(0);
        promocodeNew.setActiveCount(0);
        promocodeNew.setStatus(true);
        promocodeNew.setUserInfo(userInfo);
        promocodeRepository.save(promocodeNew);
        return ResponseEntity.ok(new ResponseData(true, "PROMOKOD yaratildi", null));
    }
    public ResponseEntity<ResponseData> allPromocode(){
        return ResponseEntity.ok(new ResponseData(true, "PROMOKODLAR", promocodeRepository.findAllPromocodes()));
    }
    public ResponseEntity<ResponseData> getUserPromocodes(HttpServletRequest request){
        UserData userData = jwtGenerator.getUserFromRequest(request);
        UserInfo userInfo = userInfoRepository.findByUserData(userData).get();
        return ResponseEntity.ok(new ResponseData(true, "User PROMOKODLARI", promocodeRepository.findByUserInfo(userInfo)));
    }
    public ResponseEntity<ResponseData> getUserPromocodeSubscribers(Long promoID,HttpServletRequest request){
        UserData userData = jwtGenerator.getUserFromRequest(request);
        UserInfo userInfo = userInfoRepository.findByUserData(userData).get();

        List<UserInfoInfo> userInfoInfoList = promocodeRepository.findByIdAndUserInfo(promoID, userInfo).getSubscribedUsers().stream().map(e->new UserInfoInfo() {
            @Override
            public Long getId() {
                return e.getId();
            }

            @Override
            public String getFirstname() {
                return e.getFirstname();
            }

            @Override
            public String getLastname() {
                return e.getLastname();
            }

            @Override
            public String getMiddlename() {
                return e.getMiddlename();
            }

            @Override
            public LocalDate getBirthday() {
                return e.getBirthday();
            }

            @Override
            public String getPrimaryPhone() {
                return e.getPrimaryPhone();
            }

            @Override
            public String getSecondaryPhone() {
                return e.getSecondaryPhone();
            }

            @Override
            public Timestamp getDate() {
                return e.getDate();
            }

            @Override
            public ImageDataInfo getProfilePhoto() {
                return null;
            }

            @Override
            public UserDataInfo getUserData() {
                return null;
            }
        }).collect(Collectors.toList());


        return ResponseEntity.ok(new ResponseData(true, "Subscribers", userInfoInfoList));
    }

    public ResponseEntity<ResponseData> changeStatus(Long promocodeID){
        Optional<Promocode> promocode = promocodeRepository.findById(promocodeID);
        if(promocode.isEmpty()){
            return new ResponseEntity<>(new ResponseData(false, "PROMOKOD topilmadi", null), HttpStatus.BAD_REQUEST);

        }
        Promocode promocode1 = promocode.get();
        promocode1.setStatus(!promocode1.getStatus());
        promocodeRepository.save(promocode1);
        return ResponseEntity.ok(new ResponseData(true, "PROMOKOD statusi o'zgardi", null));
    }
    public ResponseEntity<ResponseData> changeActiveCount(Long promocodeID, Integer activeCount){
        Optional<Promocode> promocode = promocodeRepository.findById(promocodeID);
        if(promocode.isEmpty()){
            return new ResponseEntity<>(new ResponseData(false, "PROMOKOD topilmadi", null), HttpStatus.BAD_REQUEST);

        }
        Promocode promocode1 = promocode.get();
        promocode1.setActiveCount(activeCount);
        if(activeCount==5){
            promocode1.setStatus(false);
        }
        promocodeRepository.save(promocode1);
        return ResponseEntity.ok(new ResponseData(true, "PROMOKOD aktivlar soni o'zgardi", null));
    }
}
