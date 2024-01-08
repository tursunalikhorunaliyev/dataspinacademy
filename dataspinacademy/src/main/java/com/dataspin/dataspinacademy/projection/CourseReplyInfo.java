package com.dataspin.dataspinacademy.projection;

import java.sql.Timestamp;

/**
 * Projection for {@link com.dataspin.dataspinacademy.entity.CourseReply}
 */
public interface CourseReplyInfo {
    Long getId();

    String getReplyText();

    Timestamp getDate();

    Course getCourse();

    UserInfoInfo getUserInfo();

    /**
     * Projection for {@link com.dataspin.dataspinacademy.entity.Course}
     */
    interface Course {
        Long getId();

        String getName();
    }
}