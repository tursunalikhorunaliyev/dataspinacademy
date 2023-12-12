package com.dataspin.dataspinacademy.repository;

import com.dataspin.dataspinacademy.entity.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageDataRepository extends JpaRepository<ImageData, Long> {
}