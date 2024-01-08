package com.dataspin.dataspinacademy.projection;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Projection for {@link com.dataspin.dataspinacademy.entity.DeletedComment}
 */
public interface DeletedCommentInfo {
    Long getId();

    String getReplyText();

    LocalDateTime getCommentedAt();

    Timestamp getDeletedAt();

    CourseReplyInfo.Course getCourse();

    UserInfoInfo getUserInfo();
}