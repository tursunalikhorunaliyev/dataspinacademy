package com.dataspin.dataspinacademy.projection;

/**
 * Projection for {@link com.dataspin.dataspinacademy.entity.Course}
 */
public interface CourseInfo {
    Long getId();

    String getName();

    CourseForInfo getCourseFor();

    CourseTypeInfo getCourseType();

    ImageDataInfo getPreviewPhoto();
}