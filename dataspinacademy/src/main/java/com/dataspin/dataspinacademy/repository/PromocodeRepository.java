package com.dataspin.dataspinacademy.repository;

import com.dataspin.dataspinacademy.entity.Promocode;
import com.dataspin.dataspinacademy.entity.UserInfo;
import com.dataspin.dataspinacademy.projection.PhysicalFaceInfo;
import com.dataspin.dataspinacademy.projection.PromocodeInfo;
import com.dataspin.dataspinacademy.projection.PromocodeSubscribersInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PromocodeRepository extends JpaRepository<Promocode, Long> {
    List<PromocodeInfo> findByUserInfo(UserInfo userInfo);
    Optional<Promocode> findByPromoCode(String promoCode);
    boolean existsByPromoCode(String promoCode);
    @Query("SELECT p FROM Promocode p")
    List<PromocodeInfo> findAllPromocodes();

    Promocode findByIdAndUserInfo(Long id, UserInfo userInfo);
}