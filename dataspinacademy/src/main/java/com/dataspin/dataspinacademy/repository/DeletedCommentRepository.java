package com.dataspin.dataspinacademy.repository;

import com.dataspin.dataspinacademy.entity.CourseReply;
import com.dataspin.dataspinacademy.entity.DeletedComment;
import com.dataspin.dataspinacademy.projection.CourseReplyInfo;
import com.dataspin.dataspinacademy.projection.DeletedCommentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeletedCommentRepository extends JpaRepository<DeletedComment, Long> {
    List<DeletedCommentInfo> findByCourse_Id(Long id);
    List<DeletedCommentInfo> findByCourse_IdAndUserInfo_Id(Long id, Long id1);
}