package com.dataspin.dataspinacademy.repository;

import com.dataspin.dataspinacademy.entity.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageDataRepository extends JpaRepository<ImageData, Long> {
    @Modifying
    @Query("DELETE FROM ImageData i WHERE i.id IN (:ids)")
    void deleteByIds(List<Long> ids);
}