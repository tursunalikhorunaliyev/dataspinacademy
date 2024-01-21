package com.dataspin.dataspinacademy.repository;

import com.dataspin.dataspinacademy.entity.Reception;
import com.dataspin.dataspinacademy.entity.UserData;
import com.dataspin.dataspinacademy.entity.UserInfo;
import com.dataspin.dataspinacademy.projection.CourseTypeInfo;
import com.dataspin.dataspinacademy.projection.ReceptionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReceptionRepository extends JpaRepository<Reception, Long> {

    @Query("select r from Reception r")
    List<ReceptionInfo> findAllInfo();
    List<ReceptionInfo> findByUserInfo_UserData(UserData userData);

    List<ReceptionInfo> findByCourseNameAndDateBetween(String courseName, Timestamp dateStart, Timestamp dateEnd);

    List<ReceptionInfo> findByDateBetween(Timestamp dateStart, Timestamp dateEnd);

    List<ReceptionInfo> findByCourseName(String courseName);

    boolean existsByPromoCodeAndUserInfo(String promoCode, UserInfo userInfo);





}