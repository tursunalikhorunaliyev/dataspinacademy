package com.dataspin.dataspinacademy.repository;

import com.dataspin.dataspinacademy.entity.Tag;
import com.dataspin.dataspinacademy.projection.TagInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface TagRepository extends CrudRepository<Tag, Long> {
    @Query("SELECT t FROM Tag t WHERE t.id IN (:ids)")
    Set<Tag> getByInIds(@Param("ids") List<Long> ids);
    @Query("SELECT t FROM Tag t ")
    List<TagInfo> findAllTags();
}