package com.dataspin.dataspinacademy.repository;

import com.dataspin.dataspinacademy.entity.CoursePrice;
import com.dataspin.dataspinacademy.projection.CourseInfo;
import com.dataspin.dataspinacademy.projection.CoursePriceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CoursePriceRepository extends JpaRepository<CoursePrice, Long> {
    @Query("SELECT cp FROM CoursePrice cp")
    List<CoursePriceInfo> findAllCourses();

    List<CoursePriceInfo> findByCourse_Id(Long id);
}