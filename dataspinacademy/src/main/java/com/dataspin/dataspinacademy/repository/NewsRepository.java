package com.dataspin.dataspinacademy.repository;

import com.dataspin.dataspinacademy.entity.News;
import com.dataspin.dataspinacademy.projection.NewsInfo;
import com.dataspin.dataspinacademy.projection.StuffInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    @Query("SELECT n FROM News n")
    List<NewsInfo> findAllStuff();

    @Modifying
    @Transactional
    @Query("DELETE FROM News n WHERE n.id = :id")
    void deleteManually(@Param("id") Long id);
}