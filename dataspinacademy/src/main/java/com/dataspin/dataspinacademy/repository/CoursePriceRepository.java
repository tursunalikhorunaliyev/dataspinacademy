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

    @Query(nativeQuery = true, value = "select A2.* from(select max(start_date) as start_date, course_id from course_price group by course_id) as A1 \n" +
            "inner join course_price as A2 on A1.start_date = A2.start_date;")
    List<CoursePrice> getLastPrices();
}