package com.dataspin.dataspinacademy.projection;

/**
 * Projection for {@link com.dataspin.dataspinacademy.entity.Mentor}
 */
public interface MentorInfo {
    Long getId();

    EmployeeInfo getEmployee();

    CourseInfo getCourse();
}