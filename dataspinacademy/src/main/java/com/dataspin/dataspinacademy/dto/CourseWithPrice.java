package com.dataspin.dataspinacademy.dto;

import com.dataspin.dataspinacademy.projection.CourseInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class CourseWithPrice {
    private CourseInfo course;
    private Integer price;
}
