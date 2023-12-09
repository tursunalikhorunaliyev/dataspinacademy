package com.dataspin.dataspinacademy.repository;

import com.dataspin.dataspinacademy.entity.CourseType;
import com.dataspin.dataspinacademy.projection.CourseForInfo;
import com.dataspin.dataspinacademy.projection.CourseTypeInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CourseTypeRepository extends CrudRepository<CourseType, Long> {
    @Query("select cf from CourseType cf")
    List<CourseTypeInfo> findAllInfo();

    boolean existsByName(String name);



}