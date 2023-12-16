package com.dataspin.dataspinacademy.projection;

import java.sql.Timestamp;

/**
 * Projection for {@link com.dataspin.dataspinacademy.entity.Reception}
 */
public interface ReceptionInfo {
    Long getId();

    String getReceptionNumber();

    String getCourseName();

    String getDescription();

    Timestamp getDate();

    CourseForInfo getCourseFor();

    CourseTypeInfo getCourseType();

    ImageDataInfo getCoursePhoto();

    UserInfoInfo getUserInfo();
}