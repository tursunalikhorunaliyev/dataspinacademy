package com.dataspin.dataspinacademy.repository;

import com.dataspin.dataspinacademy.entity.CourseFor;
import com.dataspin.dataspinacademy.projection.CourseForInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CourseForRepository extends CrudRepository<CourseFor, Long> {
    @Query("select cf from CourseFor cf")
    List<CourseForInfo> findAllInfo();
}