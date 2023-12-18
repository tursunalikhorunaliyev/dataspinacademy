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

    @Query(nativeQuery = true, value = "select A2.* from(select max(start_date) as start_date, course_id from course_price where start_date<now() group by course_id) as A1 \n" +
            "inner join course_price as A2 on A1.start_date = A2.start_date;")
    List<CoursePrice> getLastPrices();

    @Query(nativeQuery = true,value = "SELECT A2.* FROM (SELECT max(start_date) as x FROM dataspin224422232423.course_price where course_id=?1 and start_date<now()) as A1\n" +
            "inner join course_price as A2 on A1.x = A2.start_date")
    CoursePrice findByLastPriceByCourseId(Long id);


}