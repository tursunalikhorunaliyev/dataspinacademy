package com.dataspin.dataspinacademy.repository;

import com.dataspin.dataspinacademy.entity.UserData;
import com.dataspin.dataspinacademy.entity.UserInfo;
import com.dataspin.dataspinacademy.projection.CourseForInfo;
import com.dataspin.dataspinacademy.projection.UserInfoInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByUserData(UserData userData);
    boolean existsByUserData(UserData userData);

    @Query("SELECT ui FROM UserInfo ui")
    List<UserInfoInfo> findAllInfo();


}