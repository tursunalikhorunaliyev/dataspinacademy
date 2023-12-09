package com.dataspin.dataspinacademy.repository;

import com.dataspin.dataspinacademy.entity.CourseFor;
import com.dataspin.dataspinacademy.projection.CourseForInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CourseForRepository extends CrudRepository<CourseFor, Long> {
    @Query("select cf from CourseFor cf")
    List<CourseForInfo> findAllInfo();
}