package com.dataspin.dataspinacademy.repository;

import com.dataspin.dataspinacademy.entity.Course;
import com.dataspin.dataspinacademy.entity.Tag;
import com.dataspin.dataspinacademy.projection.CourseInfo;
import com.dataspin.dataspinacademy.projection.CourseOnlyNamesInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c")
    List<CourseInfo> findAllCourses();

    @Query("SELECT c FROM Course c")
    List<CourseOnlyNamesInfo> findAllCoursesOnlyNames();

    List<CourseInfo> findByStatus(Boolean status);

    @Query("SELECT c FROM Course c WHERE c.id IN (:ids)")
    Set<Course> getByInIds(@Param("ids") List<Long> ids);

    List<CourseInfo> findByCourseType_Id(Long id);


    @Query("SELECT c FROM Course c WHERE c.id = ?1")
    CourseInfo findByIdInfo(Long aLong);
}