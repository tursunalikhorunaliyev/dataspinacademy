package com.dataspin.dataspinacademy.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDTO {
    @NotNull(message = "Face bo'sh")
    private Long faceId;
    @NotNull(message = "Stuff bo'sh")
    private Long stuffId;
    @NotNull(message = "Practice bo'sh")
    private Integer practice;
    private Boolean isVerified;
    @NotNull(message = "Image bo'sh")
    private MultipartFile photo;
    @NotNull(message = "Start date bo'sh")
    private String startDate;
    private String endDate;

}
