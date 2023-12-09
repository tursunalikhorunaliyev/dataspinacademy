package com.dataspin.dataspinacademy.repository;

import com.dataspin.dataspinacademy.entity.Stuff;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StuffRepository extends CrudRepository<Stuff, Long> {
}