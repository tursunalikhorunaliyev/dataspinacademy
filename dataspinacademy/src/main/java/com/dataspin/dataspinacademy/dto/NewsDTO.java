package com.dataspin.dataspinacademy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
@AllArgsConstructor
public class NewsDTO {
    @NotEmpty(message = "name null bo'lishi mumkin emas")
    private String name;
    @NotEmpty(message = "shortDesc null bo'lishi mumkin emas")
    private String shortDesc;
    @NotEmpty(message = "fullDesc null bo'lishi mumkin emas")
    private String fullDesc;
    @NotNull(message = "photo null bo'lishi mumkin emas")
    private MultipartFile photo;
}
