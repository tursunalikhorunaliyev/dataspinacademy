package com.dataspin.dataspinacademy.repository;

import com.dataspin.dataspinacademy.entity.PhysicalFace;
import com.dataspin.dataspinacademy.projection.PhysicalFaceInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PhysicalFaceRepository extends CrudRepository<PhysicalFace, Long> {
    @Query("SELECT f FROM PhysicalFace f")
    List<PhysicalFaceInfo> findAllFace();
}