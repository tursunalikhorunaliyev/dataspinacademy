package com.dataspin.dataspinacademy.repository;

import com.dataspin.dataspinacademy.entity.News;
import com.dataspin.dataspinacademy.projection.StuffInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    @Query("SELECT n FROM News n ")
    List<StuffInfo> findAllStuff();
}