package com.dataspin.dataspinacademy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.*;
import java.math.BigDecimal;


@Data
@Getter
@Setter
@AllArgsConstructor
public class CoursePriceDTO {
    @NotNull(message = "course id null bo'lishi mumkin emas")
    private Long courseID;

    @NotNull(message = "price bo'sh bo'lishi mumkin emas")
    private Integer price;


    @NotNull(message = "start date null bo'lishi mumkin emas")
    private String startDate;

}
