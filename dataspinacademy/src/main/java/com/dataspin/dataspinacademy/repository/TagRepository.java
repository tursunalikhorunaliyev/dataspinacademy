package com.dataspin.dataspinacademy.repository;

import com.dataspin.dataspinacademy.entity.Tag;
import com.dataspin.dataspinacademy.projection.TagInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query("SELECT t FROM Tag t WHERE t.id IN (:ids)")
    Set<Tag> getByInIds(@Param("ids") List<Long> ids);
    @Query("SELECT t FROM Tag t ")
    List<TagInfo> findAllTags();
}