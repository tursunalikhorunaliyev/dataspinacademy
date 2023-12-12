package com.dataspin.dataspinacademy.repository;

import com.dataspin.dataspinacademy.entity.PhoneCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PhoneCodeRepository extends JpaRepository<PhoneCode, Long> {
    boolean existsByPhoneAndCode(String phone, String code);
    Optional<PhoneCode> findByCode(String code);
}