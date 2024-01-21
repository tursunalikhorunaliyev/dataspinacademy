package com.dataspin.dataspinacademy.projection;

import java.util.Set;

/**
 * Projection for {@link com.dataspin.dataspinacademy.entity.Promocode}
 */
public interface PromocodeSubscribersInfo {
    Set<UserInfoInfo> getSubscribedUsers();
}