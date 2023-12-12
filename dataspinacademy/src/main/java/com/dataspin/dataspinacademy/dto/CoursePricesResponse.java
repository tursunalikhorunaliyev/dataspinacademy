package com.dataspin.dataspinacademy.dto;

import com.dataspin.dataspinacademy.entity.Course;
import com.dataspin.dataspinacademy.projection.CourseInfo;
import com.dataspin.dataspinacademy.projection.CoursePriceInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
public class CoursePricesResponse {
    private CourseInfo courseInfo;
    private List<CoursePriceInfo> coursePriceInfo;
}
