package com.dataspin.dataspinacademy.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalFaceDTO {
    @NotBlank(message = "Ism kiritilmadi")
    private String firstname;

    @NotBlank(message = "Familiya kiritilmadi")
    private String lastname;
    @NotBlank(message = "Sharif kiritilmadi")
    private String middlename;
    @NotBlank(message = "Passport kiritilmadi")
    @Length(min = 9, max = 9, message = "Passport xato kiritildi")
    private String passport;

    private String birthday;

    @NotNull(message = "Asosiy telefon raqam kiritilmadi")
    @Length(min = 12, max = 12, message = "Telefon raqam xato kiritildi")
    private String tel1;

    private String tel2;

}
