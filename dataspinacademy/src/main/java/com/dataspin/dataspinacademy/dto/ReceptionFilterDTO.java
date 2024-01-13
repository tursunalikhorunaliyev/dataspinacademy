package com.dataspin.dataspinacademy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReceptionFilterDTO {
    private String start_date;
    private String end_date;
    private String course_name;
}
