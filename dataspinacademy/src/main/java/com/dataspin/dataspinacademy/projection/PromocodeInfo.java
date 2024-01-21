package com.dataspin.dataspinacademy.projection;

import java.sql.Timestamp;

/**
 * Projection for {@link com.dataspin.dataspinacademy.entity.Promocode}
 */
public interface PromocodeInfo {
    Long getId();

    String getPromoCode();

    Integer getTotalCount();

    Integer getActiveCount();

    Boolean getStatus();

    Timestamp getDate();

    UserInfoInfo getUserInfo();
}