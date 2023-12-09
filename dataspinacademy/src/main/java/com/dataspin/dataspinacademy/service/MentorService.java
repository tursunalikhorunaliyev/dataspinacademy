package com.dataspin.dataspinacademy.service;

import com.dataspin.dataspinacademy.dto.MentorDTO;
import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.entity.*;
import com.dataspin.dataspinacademy.repository.CourseRepository;
import com.dataspin.dataspinacademy.repository.EmployeeRepository;
import com.dataspin.dataspinacademy.repository.MentorRepository;
import com.dataspin.dataspinacademy.security.JWTGenerator;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@AllArgsConstructor
public class MentorService {

    private final JWTGenerator jwtGenerator;
    private final EmployeeRepository employeeRepository;
    private final CourseRepository courseRepository;
    private final MentorRepository mentorRepository;

    public ResponseEntity<ResponseData> create(MentorDTO mentorDTO, HttpServletRequest request){
        UserData userData = jwtGenerator.getUserFromRequest(request);
        Employee employee = employeeRepository.findById(mentorDTO.getEmployeeID()).get();
        Course course = courseRepository.findById(mentorDTO.getCourseID()).get();

        if(!employee.getStuff().getName().equals("O'qituvchi")){
            return new ResponseEntity<>(new ResponseData(false, "Bu hodim o'qituvchi lavozimida emas.", null), HttpStatus.BAD_REQUEST);
        }

        Mentor mentor = new Mentor();
        mentor.setEmployee(employee);
        mentor.setCourse(course);
        mentor.setUser(userData);

        try {
            mentorRepository.save(mentor);
            return ResponseEntity.ok(new ResponseData(true, "Ma'lumotlar saqlandi", null));
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(new ResponseData(false, "Allaqachon mavjud", null), HttpStatus.BAD_REQUEST);
        }

    }
    public ResponseEntity<ResponseData> getAll(){
        return ResponseEntity.ok(new ResponseData(true, "Barcha mentorlar", mentorRepository.findAllMentor()));
    }
}
