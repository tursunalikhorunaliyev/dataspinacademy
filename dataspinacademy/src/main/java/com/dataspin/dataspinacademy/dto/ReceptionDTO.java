package com.dataspin.dataspinacademy.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReceptionDTO {
    @NotNull(message = "courseID null bo'lishi mumkin emas")
    private Long courseID;
    private String description;
    @NotNull(message = "receptionNumber bo'sh bo'lishi mumkin emas")
    private String receptionNumber;
}
