package com.dataspin.dataspinacademy.service;

import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.entity.CourseAboutPart;
import com.dataspin.dataspinacademy.entity.ImageData;
import com.dataspin.dataspinacademy.entity.Role;
import com.dataspin.dataspinacademy.entity.UserData;
import com.dataspin.dataspinacademy.repository.CourseAboutPartRepository;
import com.dataspin.dataspinacademy.security.JWTGenerator;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class CourseAboutPartService {
    private final JWTGenerator jwtGenerator;
    private final CourseAboutPartRepository courseAboutPartRepository;

    public ResponseEntity<ResponseData> create(MultipartFile icon, String title, String description, HttpServletRequest request) throws IOException {
        UserData userData = jwtGenerator.getUserFromRequest(request);


        if(!jwtGenerator.isAdmin(userData)){
            return new ResponseEntity<>(new ResponseData(false,"Ushbu resursga murojaat qilish huquqingiz mavjud emas",null), HttpStatus.BAD_REQUEST);
        }
        CourseAboutPart courseAboutPart = new CourseAboutPart();
        ImageData imageData = new ImageData();
        imageData.setFilename(icon.getOriginalFilename());
        imageData.setContent(icon.getBytes());
        imageData.setUser(userData);

        courseAboutPart.setIcon(imageData);
        courseAboutPart.setName(title);
        courseAboutPart.setDescription(description);
        courseAboutPart.setUser(userData);
        try {
            courseAboutPartRepository.save(courseAboutPart);
            return ResponseEntity.ok(new ResponseData(true, "Ma'lumotlar saqlandi", null));
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(new ResponseData(false, "Allaqachon mavjud", null), HttpStatus.BAD_REQUEST);
        }
    }
    public ResponseEntity<ResponseData> getAll(HttpServletRequest request){
        if(!jwtGenerator.isAdmin(jwtGenerator.getUserFromRequest(request))){
            return new ResponseEntity<>(new ResponseData(false,"Ushbu resursga murojaat qilish huquqingiz mavjud emas",null), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new ResponseData(true, "Success!", courseAboutPartRepository.findAllInfo()));
    }
}
