package com.dataspin.dataspinacademy.projection;

import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * Projection for {@link com.dataspin.dataspinacademy.entity.UserInfo}
 */
public interface UserInfoInfo {
    Long getId();

    String getFirstname();

    String getLastname();

    String getMiddlename();

    LocalDate getBirthday();

    String getPrimaryPhone();

    String getSecondaryPhone();

    Timestamp getDate();
}