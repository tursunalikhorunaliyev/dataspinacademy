package com.dataspin.dataspinacademy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
@AllArgsConstructor
public class  MentorDTO {
    @NotNull(message = "Hodim kiritilmadi")
    private Long employeeID;
    @NotNull(message = "Kurs kiritilmadi")
    private String courseIDs;

    private String subMentors;
}
