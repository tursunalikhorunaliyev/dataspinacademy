package com.dataspin.dataspinacademy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;

@Data
@Getter
@Setter
@AllArgsConstructor
public class MentorDTO {
    @NotBlank(message = "Hodim kiritilmadi")
    private Long employeeID;
    @NotBlank(message = "Kurs kiritilmadi")
    private Long courseID;
}
