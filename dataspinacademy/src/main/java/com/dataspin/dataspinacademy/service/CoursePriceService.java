package com.dataspin.dataspinacademy.service;

import com.dataspin.dataspinacademy.dto.CoursePriceDTO;
import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.entity.CoursePrice;
import com.dataspin.dataspinacademy.entity.UserData;
import com.dataspin.dataspinacademy.repository.CoursePriceRepository;
import com.dataspin.dataspinacademy.repository.CourseRepository;
import com.dataspin.dataspinacademy.security.JWTGenerator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class CoursePriceService {
    private final JWTGenerator jwtGenerator;
    private final CourseRepository courseRepository;
    private final CoursePriceRepository coursePriceRepository;

    public ResponseEntity<ResponseData> create(CoursePriceDTO coursePriceDTO, HttpServletRequest request) {
        UserData userData = jwtGenerator.getUserFromRequest(request);

        List<Boolean> isAdmin = userData.getRoles().stream().map(e -> e.getName().equals("ADMIN")).toList();
        if (!isAdmin.contains(true)) {
            return new ResponseEntity<>(new ResponseData(false, "Ushbu resursga murojaat qilish uchun huquqingiz yo'q", null), HttpStatus.BAD_REQUEST);
        }
        CoursePrice coursePrice = new CoursePrice();
        coursePrice.setCourse(courseRepository.findById(coursePriceDTO.getCourseID()).get());
        coursePrice.setPrice(coursePriceDTO.getPrice());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(coursePriceDTO.getStartDate(), formatter);
        coursePrice.setStartDate(dateTime);
        coursePrice.setUser(userData);
        coursePriceRepository.save(coursePrice);

        return ResponseEntity.ok(new ResponseData(true, "Ma'lumotlar saqlandi", null));


    }

    public ResponseEntity<ResponseData> getAll() {
        //UserData userData = jwtGenerator.getUserFromRequest(request);
       /* List<Boolean> isAdmin = userData.getRoles().stream().map(e-> e.getName().equals("ADMIN")).toList();
        if(!isAdmin.contains(true)){
            return new ResponseEntity<ResponseData>(new ResponseData(false, "Ushbu resursga murojaat qila olmaysiz"))
        }*/
        return ResponseEntity.ok(new ResponseData(true, "Barcha narxlar", coursePriceRepository.findAllCourses()));
    }
    public ResponseEntity<ResponseData> getAllByCourse(Long courseID){
        return ResponseEntity.ok(new ResponseData(true, "Barcha price lar" ,coursePriceRepository.findByCourse_Id(courseID)));
    }

}