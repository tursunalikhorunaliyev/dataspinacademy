package com.dataspin.dataspinacademy.projection;

import java.sql.Timestamp;
import java.util.Set;

/**
 * Projection for {@link com.dataspin.dataspinacademy.entity.Course}
 */
public interface CourseInfo {
    Long getId();

    String getName();

    String getDescription();

    Boolean getStatus();

    Timestamp getDate();

    CourseForInfo getCourseFor();

    CourseTypeInfo getCourseType();

    ImageDataInfo getPreviewPhoto();

    Set<CourseAboutPartInfo> getCourseAboutParts();
}