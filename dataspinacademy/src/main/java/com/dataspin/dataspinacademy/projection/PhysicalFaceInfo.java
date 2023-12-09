package com.dataspin.dataspinacademy.projection;

import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * Projection for {@link com.dataspin.dataspinacademy.entity.PhysicalFace}
 */
public interface PhysicalFaceInfo {
    Long getId();

    String getFirstname();

    String getLastname();

    String getMiddlename();

    String getPassport();

    LocalDate getBirthday();

    String getTel1();

    String getTel2();

    Timestamp getDate();
}