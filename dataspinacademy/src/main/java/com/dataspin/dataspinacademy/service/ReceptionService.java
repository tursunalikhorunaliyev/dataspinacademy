package com.dataspin.dataspinacademy.service;

import com.dataspin.dataspinacademy.dto.ReceptionDTO;
import com.dataspin.dataspinacademy.dto.ReceptionResult;
import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.entity.Course;
import com.dataspin.dataspinacademy.entity.Reception;
import com.dataspin.dataspinacademy.entity.UserData;
import com.dataspin.dataspinacademy.entity.UserInfo;
import com.dataspin.dataspinacademy.projection.ReceptionInfo;
import com.dataspin.dataspinacademy.repository.CourseRepository;
import com.dataspin.dataspinacademy.repository.ReceptionRepository;
import com.dataspin.dataspinacademy.repository.UserInfoRepository;
import com.dataspin.dataspinacademy.security.JWTGenerator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@AllArgsConstructor
public class ReceptionService {

    private final JWTGenerator jwtGenerator;
    private final ReceptionRepository receptionRepository;
    private final CourseRepository courseRepository;
    private final UserInfoRepository userInfoRepository;

    public ResponseEntity<ResponseData> create(ReceptionDTO receptionDTO, HttpServletRequest request){
        UserData userData = jwtGenerator.getUserFromRequest(request);
        UserInfo userInfo = userInfoRepository.findByUserData(userData);
        Reception reception = new Reception();
        Course course = courseRepository.findById(receptionDTO.getCourseID()).get();
        reception.setReceptionNumber(receptionDTO.getReceptionNumber());
        reception.setCourseName(course.getName());
        reception.setDescription(reception.getDescription());
        reception.setCourseFor(course.getCourseFor());
        reception.setCourseType(course.getCourseType());
        reception.setCoursePhoto(course.getPreviewPhoto());
        reception.setUserInfo(userInfo);
        reception.setUser(userData);
        receptionRepository.save(reception);
        return ResponseEntity.ok(new ResponseData(true, "Ro'yxatdan o'tganligingiz uchun tashakkur. Operator javobini kuting", null));

    }
    public ResponseEntity<ResponseData> getAllByUser(HttpServletRequest request){
        UserData userData = jwtGenerator.getUserFromRequest(request);
        List<ReceptionInfo> allReceptions = receptionRepository.findByUserInfo_UserData(userData);
        return ResponseEntity.ok(new ResponseData(true, "Barcha yozilgan kurslaringiz", allReceptions));
    }
    public ResponseEntity<ResponseData> getAllByAdmin(HttpServletRequest request){
        UserData userData = jwtGenerator.getUserFromRequest(request);
        boolean isAdmin = jwtGenerator.isAdmin(userData);
        if(!isAdmin){
           return new ResponseEntity<>(new ResponseData(false, "Ushbu resursga murojaat qilish uchun huquqingiz yo'q", null), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(new ResponseData(true, "Barcha kursga yozilganlar", receptionRepository.findAllInfo()));
    }
}
