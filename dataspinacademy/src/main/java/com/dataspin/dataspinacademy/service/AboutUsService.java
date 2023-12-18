package com.dataspin.dataspinacademy.service;

import com.dataspin.dataspinacademy.dto.AboutUsDTO;
import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.entity.AboutUs;
import com.dataspin.dataspinacademy.entity.ImageData;
import com.dataspin.dataspinacademy.entity.UserData;
import com.dataspin.dataspinacademy.repository.AboutUsRepository;
import com.dataspin.dataspinacademy.repository.ReceptionRepository;
import com.dataspin.dataspinacademy.security.JWTGenerator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AboutUsService {

    private final JWTGenerator jwtGenerator;
    private final AboutUsRepository aboutUsRepository;
    private final ReceptionRepository receptionRepository;

    public ResponseEntity<ResponseData> create(AboutUsDTO aboutUsDTO, HttpServletRequest request) throws IOException {
        UserData userData = jwtGenerator.getUserFromRequest(request);
        AboutUs aboutUs = new AboutUs();
        aboutUs.setAcademyName(aboutUsDTO.getAcademy_name());
        aboutUs.setActivityDesc(aboutUsDTO.getActivity_desc());
        aboutUs.setFullAboutUs(aboutUsDTO.getFull_about_us());
        aboutUs.setYouTubeLinks(aboutUsDTO.getYoutube_links());
        aboutUs.setOurLocation(aboutUsDTO.getOur_location());

        ImageData mainPhoto = new ImageData();
        mainPhoto.setFilename(aboutUsDTO.getMain_photo().getOriginalFilename());
        mainPhoto.setContent(aboutUsDTO.getMain_photo().getBytes());
        mainPhoto.setUser(userData);

        aboutUs.setMainPhoto(mainPhoto);


        ImageData licenseImage = new ImageData();
        licenseImage.setFilename(aboutUsDTO.getLicense_photos().getOriginalFilename());
        licenseImage.setContent(aboutUsDTO.getLicense_photos().getBytes());
        licenseImage.setUser(userData);

        aboutUs.setLicensePhotos(Collections.singleton(licenseImage));

        if(aboutUsDTO.getAdditional_photo() != null){
            ImageData additionalPhoto = new ImageData();
            additionalPhoto.setFilename(aboutUsDTO.getAdditional_photo().getOriginalFilename());
            additionalPhoto.setContent(aboutUsDTO.getAdditional_photo().getBytes());
            additionalPhoto.setUser(userData);
            aboutUs.setAdditionalPhoto(Collections.singleton(additionalPhoto));
        }
        aboutUs.setUser(userData);
        aboutUsRepository.save(aboutUs);

        return ResponseEntity.ok(new ResponseData(true,"Barcha ma'lumotlar saqlandi", null));

    }
    public ResponseEntity<ResponseData> addLicensePhoto(MultipartFile photo, HttpServletRequest request) throws IOException {
        return addPhoto(photo, request, true);
    }
    public ResponseEntity<ResponseData> addAdditionalPhoto(MultipartFile photo, HttpServletRequest request) throws IOException {
        return addPhoto(photo, request, false);
    }

    private ResponseEntity<ResponseData> addPhoto(MultipartFile photo, HttpServletRequest request, boolean isLicense) throws IOException {
        UserData userData = jwtGenerator.getUserFromRequest(request);
        ImageData imageData = new ImageData();
        imageData.setFilename(photo.getOriginalFilename());
        imageData.setContent(photo.getBytes());
        imageData.setUser(userData);

        Optional<AboutUs> aboutUs = aboutUsRepository.findAll().stream().findFirst();
        if(aboutUs.isEmpty()){
            return new ResponseEntity<>(new ResponseData(false, "Oldin birorta ham ma'lumot yaratilmagan", null), HttpStatus.BAD_REQUEST);
        }
       if(isLicense){
           aboutUs.get().getLicensePhotos().add(imageData);
       }
       else{
           aboutUs.get().getAdditionalPhoto().add(imageData);
       }
        aboutUs.ifPresent(aboutUsRepository::save);
        return ResponseEntity.ok(new ResponseData(true, "Saqlandi", null));
    }

    public ResponseEntity<ResponseData> getAllInformation(){
        return ResponseEntity.ok(new ResponseData(true, "Barcha ma'lumotlar", aboutUsRepository.findAllInfo().stream().findFirst()));
    }

}
