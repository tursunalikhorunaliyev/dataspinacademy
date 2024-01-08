package com.dataspin.dataspinacademy.repository;

import com.dataspin.dataspinacademy.entity.CourseReply;
import com.dataspin.dataspinacademy.projection.CourseReplyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CourseReplyRepository extends JpaRepository<CourseReply, Long> {
    List<CourseReplyInfo> findByCourse_Id(Long id);

    List<CourseReplyInfo> findByCourse_IdAndUserInfo_Id(Long id, Long id1);
}