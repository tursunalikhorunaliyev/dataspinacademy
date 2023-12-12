package com.dataspin.dataspinacademy.projection;

/**
 * Projection for {@link com.dataspin.dataspinacademy.entity.News}
 */
public interface NewsInfo {
    Long getId();

    String getName();

    String getShortDesc();

    String getFullDesc();

    ImageDataInfo getPhoto();
}