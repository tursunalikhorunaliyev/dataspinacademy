package com.dataspin.dataspinacademy.repository;

import com.dataspin.dataspinacademy.entity.Course;
import com.dataspin.dataspinacademy.entity.CourseAboutPart;
import com.dataspin.dataspinacademy.projection.CourseAboutPartInfo;
import com.dataspin.dataspinacademy.projection.CourseForInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface CourseAboutPartRepository extends JpaRepository<CourseAboutPart, Long> {
    @Query("SELECT c FROM CourseAboutPart c WHERE c.id IN (:ids)")
    Set<CourseAboutPart> getByInIds(@Param("ids") List<Long> ids);
    @Query("SELECT cap FROM CourseAboutPart cap")
    List<CourseAboutPartInfo> findAllInfo();
}