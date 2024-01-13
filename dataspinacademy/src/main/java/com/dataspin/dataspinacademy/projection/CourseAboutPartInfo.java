package com.dataspin.dataspinacademy.projection;

/**
 * Projection for {@link com.dataspin.dataspinacademy.entity.CourseAboutPart}
 */
public interface CourseAboutPartInfo {
    Long getId();

    String getName();

    String getDescription();

    ImageDataInfo getIcon();
}