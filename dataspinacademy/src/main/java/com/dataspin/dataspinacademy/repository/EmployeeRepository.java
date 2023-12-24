package com.dataspin.dataspinacademy.repository;

import com.dataspin.dataspinacademy.entity.Employee;
import com.dataspin.dataspinacademy.entity.Tag;
import com.dataspin.dataspinacademy.projection.CourseTypeInfo;
import com.dataspin.dataspinacademy.projection.EmployeeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e")
    List<EmployeeInfo> findAllInfo();

    List<EmployeeInfo> findByStuff_Name(String name);

    @Query("SELECT e FROM Employee e WHERE e.id IN (:ids)")
    Set<Employee> getByInIds(@Param("ids") List<Long> ids);


}