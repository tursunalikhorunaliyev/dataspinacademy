package com.dataspin.dataspinacademy.service;

import com.dataspin.dataspinacademy.dto.CourseForDTO;
import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.entity.CourseFor;
import com.dataspin.dataspinacademy.entity.UserData;
import com.dataspin.dataspinacademy.repository.CourseForRepository;
import com.dataspin.dataspinacademy.security.JWTGenerator;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@AllArgsConstructor
public class CourseForService {

    private final JWTGenerator jwtGenerator;
    private final CourseForRepository courseForRepository;

    public ResponseEntity<ResponseData> create(CourseForDTO courseFor, HttpServletRequest request) {
        final UserData userData = jwtGenerator.getUserFromRequest(request);
        final CourseFor courseForData = new CourseFor();
        courseForData.setName(courseFor.getName());
        courseForData.setDescription(courseFor.getDescription());
        courseForData.setUserData(userData);
        try {
            courseForRepository.save(courseForData);
            return ResponseEntity.ok(new ResponseData(true, "Ma'lumotlar saqlandi", null));
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(new ResponseData(false, "Allaqachon mavjud", null), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<ResponseData> getAll() {
        return ResponseEntity.ok(new ResponseData(true, "Succesfull", courseForRepository.findAllInfo()));
    }
}
