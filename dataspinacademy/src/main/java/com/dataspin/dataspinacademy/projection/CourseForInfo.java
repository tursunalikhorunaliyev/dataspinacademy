package com.dataspin.dataspinacademy.projection;

import java.sql.Timestamp;

/**
 * Projection for {@link com.dataspin.dataspinacademy.entity.CourseFor}
 */
public interface CourseForInfo {
    Long getId();

    String getName();

    String getDescription();

    Timestamp getDate();
}