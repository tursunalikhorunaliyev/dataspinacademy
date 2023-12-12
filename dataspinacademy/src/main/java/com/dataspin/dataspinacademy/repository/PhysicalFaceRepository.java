package com.dataspin.dataspinacademy.repository;

import com.dataspin.dataspinacademy.entity.PhysicalFace;
import com.dataspin.dataspinacademy.projection.PhysicalFaceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PhysicalFaceRepository extends JpaRepository<PhysicalFace, Long> {
    @Query("SELECT f FROM PhysicalFace f")
    List<PhysicalFaceInfo> findAllFace();
}