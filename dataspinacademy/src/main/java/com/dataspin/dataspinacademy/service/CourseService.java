package com.dataspin.dataspinacademy.service;


import com.dataspin.dataspinacademy.dto.CourseWithPrice;
import com.dataspin.dataspinacademy.dto.ReceptionCounterData;
import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.entity.*;
import com.dataspin.dataspinacademy.projection.CourseInfo;
import com.dataspin.dataspinacademy.projection.CoursePriceInfo;
import com.dataspin.dataspinacademy.projection.MentorInfoOnlyEmployeeAndCoursesId;
import com.dataspin.dataspinacademy.repository.*;
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
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseForRepository courseForRepository;
    private final CourseTypeRepository courseTypeRepository;
    private final CourseRepository courseRepository;
    private final JWTGenerator jwtGenerator;
    private final CoursePriceRepository coursePriceRepository;
    private final ReceptionCounterRepository receptionCounterRepository;
    private final MentorRepository mentorRepository;


    public ResponseEntity<ResponseData> create(String name,String description, Long forId, Long typeId, MultipartFile photo, Boolean status, HttpServletRequest request) throws IOException {

        UserData userData = jwtGenerator.getUserFromRequest(request);
        Course course = new Course();
        course.setName(name);
        course.setDescription(description);
        course.setCourseFor(courseForRepository.findById(forId).get());
        course.setCourseType(courseTypeRepository.findById(typeId).get());
        course.setUser(userData);
        course.setStatus(status);
        ImageData imageData = new ImageData();
        imageData.setFilename(photo.getOriginalFilename());
        imageData.setContent(photo.getBytes());
        imageData.setUser(userData);
        course.setPreviewPhoto(imageData);
        try {
            courseRepository.save(course);
            ReceptionCounter receptionCounter = new ReceptionCounter();
            receptionCounter.setActiveCount(0);
            receptionCounter.setInactiveCount(0);
            receptionCounter.setTotalCount(0);
            receptionCounter.setCourse(course);
            receptionCounterRepository.save(receptionCounter);
            return ResponseEntity.ok(new ResponseData(true, "Ma'lumotlar saqlandi", null));
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(new ResponseData(false, "Allaqachon mavjud", null), HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<ResponseData> getAll(){
        return ResponseEntity.ok(new ResponseData(true, "Barcha kurslar", courseRepository.findAllCourses()));
    }
    public ResponseEntity<ResponseData> getAllByStatus(Boolean isActive){
        return ResponseEntity.ok(new ResponseData(true, "Barcha kurslar", courseRepository.findByStatus(isActive)));
    }

    public ResponseEntity<ResponseData> getWithPrice(){
        List<CourseInfo> courseList = courseRepository.findAllCourses();
        List<CoursePrice> lastPrices = coursePriceRepository.getLastPrices();
        List<ReceptionCounter> receptionCounters = receptionCounterRepository.findAll();
        List<MentorInfoOnlyEmployeeAndCoursesId> mentors = mentorRepository.findAllMentorOnlyEmployeeAndCourseIds();



        List<CourseWithPrice> courseWithPrices =  courseList.stream().map(e->{

            CourseWithPrice courseWithPrice = new CourseWithPrice();
            courseWithPrice.setCourse(e);
            lastPrices.forEach(priceData->{
                if(priceData.getCourse().getId().equals(e.getId())){
                    courseWithPrice.setPrice(priceData.getPrice());
                }
            });
            receptionCounters.forEach(receptionCounter -> {
                if(Objects.equals(receptionCounter.getCourse().getId(), e.getId())){

                    courseWithPrice.setReception_counter(new ReceptionCounterData(receptionCounter.getTotalCount(), receptionCounter.getActiveCount() , receptionCounter.getInactiveCount()));
                }
            });
            mentors.forEach(mentor->{
                mentor.getCourses().forEach(course->{
                    if(course.getId().equals(e.getId())){
                        courseWithPrice.setMentor(mentor);
                    }
                });
            });
            return courseWithPrice;
        }).toList();
        return  ResponseEntity.ok(new ResponseData(true, "Barcha kurslar", courseWithPrices));
    }
    public ResponseEntity<ResponseData> getCoursesByType(Long id){
        if(!courseTypeRepository.existsById(id)){
            return new ResponseEntity<>(new ResponseData(false, "Ma'lumotlar topilmadi", null), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new ResponseData(true, "Success", courseRepository.findByCourseType_Id(id)));
    }
    public ResponseEntity<ResponseData> courseWithLastPrice(Long courseId){
       CourseInfo course = courseRepository.findByIdInfo(courseId);
       CoursePrice coursePrice = coursePriceRepository.findByLastPriceByCourseId(courseId);
       CourseWithPrice courseWithPrice = new CourseWithPrice();
       courseWithPrice.setCourse(course);
       if(coursePrice==null){
           courseWithPrice.setPrice(null);
       }
       else{
           courseWithPrice.setPrice(coursePrice.getPrice());
       }

       return ResponseEntity.ok(new ResponseData(true, "Ma'lumotlar", courseWithPrice));
    }
}
