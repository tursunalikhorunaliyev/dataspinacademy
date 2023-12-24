package com.dataspin.dataspinacademy.repository;

import com.dataspin.dataspinacademy.entity.UserData;
import com.dataspin.dataspinacademy.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByUserData(UserData userData);
    boolean existsByUserData(UserData userData);
}