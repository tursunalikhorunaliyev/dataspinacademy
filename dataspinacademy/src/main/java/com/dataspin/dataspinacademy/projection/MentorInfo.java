package com.dataspin.dataspinacademy.projection;

import java.sql.Timestamp;
import java.util.Set;

/**
 * Projection for {@link com.dataspin.dataspinacademy.entity.Mentor}
 */
public interface MentorInfo {
    Long getId();

    Timestamp getDate();

    EmployeeInfo getEmployee();

    Set<CourseInfo> getCourses();

    Set<EmployeeInfo> getSubMentors();

    String getYouTubeLinks();
}