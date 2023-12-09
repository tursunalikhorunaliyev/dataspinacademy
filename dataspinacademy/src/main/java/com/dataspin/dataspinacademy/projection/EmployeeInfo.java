package com.dataspin.dataspinacademy.projection;

import java.time.LocalDateTime;

/**
 * Projection for {@link com.dataspin.dataspinacademy.entity.Employee}
 */
public interface EmployeeInfo {
    Long getId();

    Integer getPractice();

    Boolean getIsVerified();

    LocalDateTime getStartDate();

    LocalDateTime getEndDate();

    PhysicalFaceInfo getFace();

    StuffInfo getStuff();

    ImageDataInfo getPhoto();
}