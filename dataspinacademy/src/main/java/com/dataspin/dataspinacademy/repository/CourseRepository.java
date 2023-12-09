package com.dataspin.dataspinacademy.repository;

import com.dataspin.dataspinacademy.entity.Course;
import com.dataspin.dataspinacademy.projection.CourseInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course, Long> {

    @Query("SELECT c FROM Course c")
    List<CourseInfo> findAllCourses();
}