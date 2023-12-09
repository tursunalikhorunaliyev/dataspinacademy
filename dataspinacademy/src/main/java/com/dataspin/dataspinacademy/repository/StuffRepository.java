package com.dataspin.dataspinacademy.repository;

import com.dataspin.dataspinacademy.entity.Stuff;
import org.springframework.data.repository.CrudRepository;

public interface StuffRepository extends CrudRepository<Stuff, Long> {
}