package com.dataspin.dataspinacademy.projection;

import java.sql.Timestamp;
import java.util.Set;

/**
 * Projection for {@link com.dataspin.dataspinacademy.entity.CourseType}
 */
public interface CourseTypeInfo {
    Long getId();

    String getName();

    String getDescription();

    Timestamp getDate();

    ImageDataInfo1 getPhoto();

    Set<TagInfo1> getCourseTags();

    /**
     * Projection for {@link com.dataspin.dataspinacademy.entity.ImageData}
     */
    interface ImageDataInfo1 {
        Long getId();
    }

    /**
     * Projection for {@link com.dataspin.dataspinacademy.entity.Tag}
     */
    interface TagInfo1 {
        Long getId();

        String getName();

        Timestamp getDate();
    }
}