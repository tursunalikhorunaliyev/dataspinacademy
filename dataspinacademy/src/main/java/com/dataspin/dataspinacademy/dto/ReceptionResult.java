package com.dataspin.dataspinacademy.dto;

import com.dataspin.dataspinacademy.entity.CourseFor;
import com.dataspin.dataspinacademy.entity.CourseType;
import com.dataspin.dataspinacademy.projection.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
public class ReceptionResult {
    private String receptionNumber;
    private String courseName;
    private CourseTypeInfo courseType;
    private CourseForInfo courseFor;

    private ImageDataInfo imageDataInfo;
    private LocalDateTime localDateTime;

    private UserInfoInfo userInfoInfo;




}
