package com.dataspin.dataspinacademy.repository;

import com.dataspin.dataspinacademy.entity.Stuff;
import com.dataspin.dataspinacademy.projection.StuffInfo;
import com.dataspin.dataspinacademy.projection.TagInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StuffRepository extends JpaRepository<Stuff, Long> {
    @Query("SELECT s FROM Stuff s ")
    List<StuffInfo> findAllStuff();
}