package com.dataspin.dataspinacademy.projection;

import java.sql.Timestamp;

/**
 * Projection for {@link com.dataspin.dataspinacademy.entity.Tag}
 */
public interface TagInfo {
    Long getId();

    String getName();

    Timestamp getDate();
}