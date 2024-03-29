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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MentorService {

    private final JWTGenerator jwtGenerator;
    private final EmployeeRepository employeeRepository;
    private final CourseRepository courseRepository;
    private final MentorRepository mentorRepository;

    public ResponseEntity<ResponseData> create(MentorDTO mentorDTO, HttpServletRequest request) {
        UserData userData = jwtGenerator.getUserFromRequest(request);
        Employee employee = employeeRepository.findById(mentorDTO.getEmployeeID()).get();


        if (!employee.getStuff().getName().equals("Mentor")) {
            return new ResponseEntity<>(new ResponseData(false, "Bu hodim o'qituvchi lavozimida emas.", null), HttpStatus.BAD_REQUEST);
        }

        Mentor mentor = new Mentor();
        if (mentorDTO.getCourseIDs().length() == 1) {
            Optional<Course> course = courseRepository.findById(Long.parseLong(mentorDTO.getCourseIDs()));
            if (course.isEmpty()) {
                return new ResponseEntity<>(new ResponseData(false, "Bunday kurs topilmadi", null), HttpStatus.BAD_REQUEST);
            }
            mentor.setCourses(Collections.singleton(course.get()));
        } else {
            List<Long> courseIDs = Arrays.stream(mentorDTO.getCourseIDs().split(",")).map(Long::parseLong).collect(Collectors.toList());
            Set<Course> courses = courseRepository.getByInIds(courseIDs);
            if (courses.isEmpty()) {
                return new ResponseEntity<>(new ResponseData(false, "Bunday kurslar topilmadi", null), HttpStatus.BAD_REQUEST);
            }
            mentor.setCourses(courses);
        }
        mentor.setEmployee(employee);
        if (mentorDTO.getSubMentors() != null) {
            mentor.setSubMentors(new HashSet<>(employeeRepository.getByInIds(Arrays.stream(mentorDTO.getSubMentors().split(",")).map(Long::parseLong).toList())));
        }
        else{
            mentor.setSubMentors(null);
        }
        mentor.setYouTubeLinks(mentorDTO.getYouTubeLink());
        mentor.setUser(userData);
        try {
            mentorRepository.save(mentor);
            return ResponseEntity.ok(new ResponseData(true, "Ma'lumotlar saqlandi", null));
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(new ResponseData(false, "Allaqachon mavjud", null), HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<ResponseData> update(Long id, String courseIds, String subMentors, String youtubeLinks, HttpServletRequest request) {
        UserData userData = jwtGenerator.getUserFromRequest(request);
        Optional<Mentor> oldMentor = mentorRepository.findById(id);

        if (oldMentor.isEmpty()) {
            return new ResponseEntity<>(new ResponseData(false, "Mentor topilmadi.", null), HttpStatus.BAD_REQUEST);
        }

        if (!oldMentor.get().getEmployee().getStuff().getName().equals("Mentor")) {
            return new ResponseEntity<>(new ResponseData(false, "Bu hodim o'qituvchi lavozimida emas.", null), HttpStatus.BAD_REQUEST);
        }

        Mentor mentor = new Mentor();

        mentor.setId(oldMentor.get().getId());
        mentor.setEmployee(oldMentor.get().getEmployee());

        if (courseIds.length() == 1) {
            Optional<Course> course = courseRepository.findById(Long.parseLong(courseIds));
            if (course.isEmpty()) {
                return new ResponseEntity<>(new ResponseData(false, "Bunday kurs topilmadi", null), HttpStatus.BAD_REQUEST);
            }
            mentor.setCourses(Collections.singleton(course.get()));
        } else {
            List<Long> courseIDs = Arrays.stream(courseIds.split(",")).map(Long::parseLong).collect(Collectors.toList());
            Set<Course> courses = courseRepository.getByInIds(courseIDs);
            if (courses.isEmpty()) {
                return new ResponseEntity<>(new ResponseData(false, "Bunday kurslar topilmadi", null), HttpStatus.BAD_REQUEST);
            }
            mentor.setCourses(courses);
        }
        if (subMentors != null) {
            mentor.setSubMentors(new HashSet<>(employeeRepository.getByInIds(Arrays.stream(subMentors.split(",")).map(Long::parseLong).toList())));
        }
        mentor.setUser(userData);
        mentor.setYouTubeLinks(youtubeLinks);
        try {
            mentorRepository.save(mentor);
            return ResponseEntity.ok(new ResponseData(true, "Ma'lumotlar yangilandi", null));
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(new ResponseData(false, "Allaqachon mavjud", null), HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<ResponseData> getAll() {
        return ResponseEntity.ok(new ResponseData(true, "Barcha mentorlar", mentorRepository.findAllMentor()));
    }
    public ResponseEntity<ResponseData> uploadCV(MultipartFile cv, Long mentorId, HttpServletRequest request) throws IOException {
        UserData userData = jwtGenerator.getUserFromRequest(request);
        if(jwtGenerator.isAdmin(userData)){
            Mentor mentor = mentorRepository.findById(mentorId).get();
            ImageData imageData = new ImageData();
            imageData.setFilename(cv.getOriginalFilename());
            imageData.setContent(cv.getBytes());
            imageData.setUser(userData);
            mentor.setCv(imageData);
            mentorRepository.save(mentor);
            return ResponseEntity.ok(new ResponseData(true, "Saqlandi", null));
        }
        else{
            return new ResponseEntity<>(new ResponseData(false, "Allaqachon mavjud", null), HttpStatus.BAD_REQUEST);
        }
    }
}
