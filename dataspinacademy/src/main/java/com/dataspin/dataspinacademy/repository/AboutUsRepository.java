package com.dataspin.dataspinacademy.repository;

import com.dataspin.dataspinacademy.entity.AboutUs;
import com.dataspin.dataspinacademy.projection.AboutUsInfo;
import com.dataspin.dataspinacademy.projection.ReceptionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AboutUsRepository extends JpaRepository<AboutUs, Long> {
    @Query("select a from AboutUs a")
    List<AboutUsInfo> findAllInfo();
}