package com.dataspin.dataspinacademy.projection;

import java.util.Set;

/**
 * Projection for {@link com.dataspin.dataspinacademy.entity.AboutUs}
 */
public interface AboutUsInfo {
    Long getId();

    String getAcademyName();

    String getActivityDesc();

    String getFullAboutUs();

    String getYouTubeLinks();

    String getOurLocation();

    ImageDataInfo getMainPhoto();

    Set<ImageDataInfo> getLicensePhotos();

    Set<ImageDataInfo> getAdditionalPhoto();
}