package com.dataspin.dataspinacademy.projection;

/**
 * Projection for {@link com.dataspin.dataspinacademy.entity.ReceptionCounter}
 */
public interface ReceptionCounterInfo {
    Integer getCount();

    Integer getActiveCount();

    Integer getInactiveCount();
}