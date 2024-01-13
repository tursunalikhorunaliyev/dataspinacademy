package com.dataspin.dataspinacademy.service;
import com.dataspin.dataspinacademy.dto.ReceptionDTO;
import com.dataspin.dataspinacademy.dto.ReceptionFilterDTO;
import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.entity.*;
import com.dataspin.dataspinacademy.projection.ReceptionInfo;
import com.dataspin.dataspinacademy.repository.*;
import com.dataspin.dataspinacademy.security.JWTGenerator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReceptionService {

    private final JWTGenerator jwtGenerator;
    private final ReceptionRepository receptionRepository;
    private final CourseRepository courseRepository;
    private final UserInfoRepository userInfoRepository;
    private final ReceptionCounterRepository receptionCounterRepository;
    private final UserDataRepository userDataRepository;

    public ResponseEntity<ResponseData> create(ReceptionDTO receptionDTO, HttpServletRequest request){
        Reception reception = new Reception();
        if(receptionDTO.getUser_id()!=null){
          UserData userData =  userDataRepository.findById(receptionDTO.getUser_id()).orElse(null);

          if(userData==null){
              return new ResponseEntity<>(new ResponseData(false, "User topilmadi", null), HttpStatus.BAD_REQUEST);
          }
          else{
              UserInfo userInfo = userInfoRepository.findByUserData(userData).orElse(null);
              if(userInfo==null){
                  return new ResponseEntity<>(new ResponseData(false, "User info topilmadi", null), HttpStatus.BAD_REQUEST);
              }
              reception.setUser(userData);
              reception.setUserInfo(userInfo);
          }
        }
        else{
            UserData userData = jwtGenerator.getUserFromRequest(request);
            Optional<UserInfo> userInfo = userInfoRepository.findByUserData(userData);
            if(userInfo.isEmpty()){
                return new ResponseEntity<>(new ResponseData(false, "Siz ro'yxatdan o'tmagansiz", null), HttpStatus.BAD_REQUEST);
            }
            reception.setUserInfo(userInfo.get());
            reception.setUser(userData);
        }
        Course course = courseRepository.findById(receptionDTO.getCourseID()).get();
        reception.setReceptionNumber(receptionDTO.getReceptionNumber());
        reception.setCourseName(course.getName());
        reception.setDescription(receptionDTO.getDescription());
        reception.setCourseFor(course.getCourseFor());
        reception.setCourseType(course.getCourseType());
        reception.setCoursePhoto(course.getPreviewPhoto());
        receptionRepository.save(reception);
        ReceptionCounter receptionCounter = receptionCounterRepository.findByCourse_Id(receptionDTO.getCourseID());
        receptionCounter.setActiveCount(receptionCounter.getActiveCount()+1);
        receptionCounterRepository.save(receptionCounter);
        return ResponseEntity.ok(new ResponseData(true, "Ro'yxatdan o'tganligingiz uchun tashakkur. Operator javobini kuting", null));

    }
    public ResponseEntity<ResponseData> getAllByUser(HttpServletRequest request){
        UserData userData = jwtGenerator.getUserFromRequest(request);
        List<ReceptionInfo> allReceptions = receptionRepository.findByUserInfo_UserData(userData);
        return ResponseEntity.ok(new ResponseData(true, "Barcha yozilgan kurslaringiz", allReceptions));
    }
    public ResponseEntity<ResponseData> getAllByAdmin(String courseName, String startDate, String endDate, HttpServletRequest request) throws ParseException {
        UserData userData = jwtGenerator.getUserFromRequest(request);
        boolean isAdmin = jwtGenerator.isAdmin(userData);
        if(!isAdmin){
           return new ResponseEntity<>(new ResponseData(false, "Ushbu resursga murojaat qilish uchun huquqingiz yo'q", null), HttpStatus.BAD_REQUEST);
        }
        if(courseName==null && startDate==null && endDate==null){
            return ResponseEntity.ok(new ResponseData(true, "Barcha kursga yozilganlar", receptionRepository.findAllInfo()));
        }
        else{
            if(courseName==null && startDate!=null && endDate!=null){
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date start = dateFormat.parse(startDate);
                Timestamp timestampStart = new java.sql.Timestamp(start.getTime());
                Date end = dateFormat.parse(endDate);
                Timestamp timestampEnd = new java.sql.Timestamp(end.getTime());
                return ResponseEntity.ok(new ResponseData(true, "Receptions in the interval",receptionRepository.findByDateBetween(timestampStart, timestampEnd)));
            }
            else if(courseName!= null && startDate==null && endDate==null){
                return ResponseEntity.ok(new ResponseData(true, "Receptions by course name", receptionRepository.findByCourseName(courseName)));
            }
            else if(courseName != null && startDate!=null && endDate!=null){
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date start = dateFormat.parse(startDate);
                Timestamp timestampStart = new java.sql.Timestamp(start.getTime());
                Date end = dateFormat.parse(endDate);
                Timestamp timestampEnd = new java.sql.Timestamp(end.getTime());

                return ResponseEntity.ok(new ResponseData(true, "Success!", receptionRepository.findByCourseNameAndDateBetween(courseName, timestampStart, timestampEnd)));
            }
        }
        return null;
    }
}
