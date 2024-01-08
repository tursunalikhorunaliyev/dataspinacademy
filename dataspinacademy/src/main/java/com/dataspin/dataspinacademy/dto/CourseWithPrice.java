package com.dataspin.dataspinacademy.dto;

import com.dataspin.dataspinacademy.entity.ReceptionCounter;
import com.dataspin.dataspinacademy.projection.CourseInfo;
import com.dataspin.dataspinacademy.projection.MentorInfo;
import com.dataspin.dataspinacademy.projection.MentorInfoOnlyEmployeeAndCoursesId;
import lombok.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;

import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseWithPrice {
    private CourseInfo course;

    private MentorInfoOnlyEmployeeAndCoursesId mentor;
    private Integer price;

    private ReceptionCounterData reception_counter;
}

