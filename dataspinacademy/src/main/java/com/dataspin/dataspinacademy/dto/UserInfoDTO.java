package com.dataspin.dataspinacademy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO {
    @NotBlank(message = "Ism kiritilmadi")
    private String firstname;
    @NotBlank(message = "Familiya kiritilmadi")
    private String lastname;
    private String middlename;
    private String birthday;
    @NotBlank(message = "Asosiy telefon raqami kiritilmadi")
    private String tel1;
    private String tel2;

    private MultipartFile profilePhoto;

    @NotBlank(message = "Username kiritilmadi")
    private String username;

    @NotBlank(message = "Password kiritilmadi")
    private String password;

}
