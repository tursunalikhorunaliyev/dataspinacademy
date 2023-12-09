package com.dataspin.dataspinacademy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Data
public class CourseForDTO {

    @NotBlank(message = "Nomi bo'sh bo'lishi mukin emas")
    private String name;
    private String description;




}
