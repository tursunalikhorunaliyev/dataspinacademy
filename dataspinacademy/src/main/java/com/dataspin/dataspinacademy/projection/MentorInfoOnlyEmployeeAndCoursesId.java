package com.dataspin.dataspinacademy.projection;

import java.util.Set;

/**
 * Projection for {@link com.dataspin.dataspinacademy.entity.Mentor}
 */
public interface MentorInfoOnlyEmployeeAndCoursesId {
    Set<CourseInfoOnlyId> getCourses();

    EmployeeInfo getEmployee();

    /**
     * Projection for {@link com.dataspin.dataspinacademy.entity.Course}
     */
    interface CourseInfoOnlyId {
        Long getId();
    }
}