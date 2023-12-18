package com.dataspin.dataspinacademy.repository;

import com.dataspin.dataspinacademy.entity.ReceptionCounter;
import com.dataspin.dataspinacademy.projection.PhysicalFaceInfo;
import com.dataspin.dataspinacademy.projection.ReceptionCounterInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceptionCounterRepository extends JpaRepository<ReceptionCounter, Long> {
    ReceptionCounter findByCourse_Id(Long id);
}