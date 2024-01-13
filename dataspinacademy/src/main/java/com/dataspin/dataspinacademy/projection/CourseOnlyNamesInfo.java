package com.dataspin.dataspinacademy.projection;

/**
 * Projection for {@link com.dataspin.dataspinacademy.entity.Course}
 */
public interface CourseOnlyNamesInfo {
    Long getId();

    String getName();

    String getDescription();

    Boolean getStatus();

    CourseForInfo1 getCourseFor();

    CourseTypeInfo1 getCourseType();

    /**
     * Projection for {@link com.dataspin.dataspinacademy.entity.CourseFor}
     */
    interface CourseForInfo1 {
        String getName();
    }

    /**
     * Projection for {@link com.dataspin.dataspinacademy.entity.CourseType}
     */
    interface CourseTypeInfo1 {
        String getName();
    }
}