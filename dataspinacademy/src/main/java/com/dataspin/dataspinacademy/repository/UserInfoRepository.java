package com.dataspin.dataspinacademy.repository;

import com.dataspin.dataspinacademy.entity.UserData;
import com.dataspin.dataspinacademy.entity.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {
    boolean existsByUserData(UserData userData);
}