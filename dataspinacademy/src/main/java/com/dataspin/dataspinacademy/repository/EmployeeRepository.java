package com.dataspin.dataspinacademy.repository;

import com.dataspin.dataspinacademy.entity.Employee;
import com.dataspin.dataspinacademy.projection.CourseTypeInfo;
import com.dataspin.dataspinacademy.projection.EmployeeInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e")
    List<EmployeeInfo> findAllInfo();
}