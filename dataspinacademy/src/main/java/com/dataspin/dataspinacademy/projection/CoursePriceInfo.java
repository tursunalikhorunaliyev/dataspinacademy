package com.dataspin.dataspinacademy.projection;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Projection for {@link com.dataspin.dataspinacademy.entity.CoursePrice}
 */
public interface CoursePriceInfo {
    Long getId();

    Integer getPrice();

    LocalDateTime getStartDate();

    Timestamp getDate();

    CourseInfoOnlyID getCourse();

    /**
     * Projection for {@link com.dataspin.dataspinacademy.entity.Course}
     */
    interface CourseInfoOnlyID {
        Long getId();
    }
}