package com.dataspin.dataspinacademy.service;


import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.entity.Course;
import com.dataspin.dataspinacademy.entity.ImageData;
import com.dataspin.dataspinacademy.entity.UserData;
import com.dataspin.dataspinacademy.repository.CourseForRepository;
import com.dataspin.dataspinacademy.repository.CourseRepository;
import com.dataspin.dataspinacademy.repository.CourseTypeRepository;
import com.dataspin.dataspinacademy.security.JWTGenerator;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseForRepository courseForRepository;
    private final CourseTypeRepository courseTypeRepository;
    private final CourseRepository courseRepository;
    private final JWTGenerator jwtGenerator;


    public ResponseEntity<ResponseData> create(Long forId, Long typeId, MultipartFile photo, HttpServletRequest request) throws IOException {
        UserData userData = jwtGenerator.getUserFromRequest(request);
        Course course = new Course();
        course.setCourseFor(courseForRepository.findById(forId).get());
        course.setCourseType(courseTypeRepository.findById(typeId).get());
        course.setUser(userData);
        ImageData imageData = new ImageData();
        imageData.setFilename(photo.getOriginalFilename());
        imageData.setContent(photo.getBytes());
        imageData.setUser(userData);
        course.setPreviewPhoto(imageData);
        try {
            courseRepository.save(course);
            return ResponseEntity.ok(new ResponseData(true, "Ma'lumotlar saqlandi", null));
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(new ResponseData(false, "Allaqachon mavjud", null), HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<ResponseData> getAll(){
        return ResponseEntity.ok(new ResponseData(true, "Barcha kurslar", courseRepository.findAllCourses()));
    }
}
