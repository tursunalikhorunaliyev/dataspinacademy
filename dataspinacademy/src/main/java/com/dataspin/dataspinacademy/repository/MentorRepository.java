package com.dataspin.dataspinacademy.repository;

import com.dataspin.dataspinacademy.entity.Mentor;
import com.dataspin.dataspinacademy.projection.MentorInfo;
import com.dataspin.dataspinacademy.projection.PhysicalFaceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MentorRepository extends JpaRepository<Mentor, Long> {
    @Query("SELECT m FROM Mentor m")
    List<MentorInfo> findAllMentor();
}