package com.dataspin.dataspinacademy.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
public class AboutUsDTO {
    @NotEmpty(message = "academyName null bo'lishi mumkin emas")
    private String academy_name;
    @NotEmpty(message = "activityDesc null bo'lishi mumkin emas")
    private String activity_desc;
    @NotEmpty(message = "fullAboutUs null bo'lishi mukin emas")
    private String full_about_us;
    private String youtube_links;
    @NotEmpty(message = "ourLocation null bo'lishi mumkin emas")
    private String our_location;
    @NotNull(message = "mainPhoto null bo'lishi mumkin emas")
    private MultipartFile main_photo;
    @NotNull(message = "mainPhoto null bo'lishi mumkin emas")
    private MultipartFile[] license_photos;
    private MultipartFile[] additional_photos;

}
