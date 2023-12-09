package com.dataspin.dataspinacademy.repository;

import com.dataspin.dataspinacademy.entity.PhoneCode;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PhoneCodeRepository extends CrudRepository<PhoneCode, Long> {
    boolean existsByPhoneAndCode(String phone, String code);
    Optional<PhoneCode> findByCode(String code);
}