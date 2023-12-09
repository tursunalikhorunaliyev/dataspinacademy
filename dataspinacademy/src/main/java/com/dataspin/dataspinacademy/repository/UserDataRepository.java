package com.dataspin.dataspinacademy.repository;

import com.dataspin.dataspinacademy.entity.UserData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserDataRepository extends CrudRepository<UserData, Long> {
    boolean existsByPhone(String phone);
    Optional<UserData> findByPhone(String phone);
}