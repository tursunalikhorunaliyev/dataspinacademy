package com.dataspin.dataspinacademy.service;

import com.dataspin.dataspinacademy.dto.AboutUsDTO;
import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.entity.AboutUs;
import com.dataspin.dataspinacademy.entity.ImageData;
import com.dataspin.dataspinacademy.entity.UserData;
import com.dataspin.dataspinacademy.repository.AboutUsRepository;
import com.dataspin.dataspinacademy.repository.ImageDataRepository;
import com.dataspin.dataspinacademy.security.JWTGenerator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AboutUsService {

    private final JWTGenerator jwtGenerator;
    private final AboutUsRepository aboutUsRepository;
    private final ImageDataRepository imageDataRepository;

    public ResponseEntity<ResponseData> create(AboutUsDTO aboutUsDTO, HttpServletRequest request) throws IOException {
        UserData userData = jwtGenerator.getUserFromRequest(request);
        final List<AboutUs> a = aboutUsRepository.findAll();
        if (a.size() == 1) {
            List<Long> additionalImageIds = a.get(0).getAdditionalPhoto().stream().map(ImageData::getId).toList();
            List<Long> licenseImageIds = a.get(0).getLicensePhotos().stream().map(ImageData::getId).toList();
            aboutUsRepository.deleteById(a.get(0).getId());
            if (!additionalImageIds.isEmpty()) {
                imageDataRepository.deleteByIds(additionalImageIds);
            }
            if (!licenseImageIds.isEmpty()) {
                imageDataRepository.deleteByIds(licenseImageIds);
            }
        }
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


        Set<ImageData> licensePhotos = Arrays.stream(aboutUsDTO.getLicense_photos()).map(photo -> {
            ImageData imageData = new ImageData();
            imageData.setFilename(photo.getOriginalFilename());
            try {
                imageData.setContent(photo.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            imageData.setUser(userData);
            return imageData;

        }).collect(Collectors.toSet());
        aboutUs.setLicensePhotos(licensePhotos);

        if (aboutUsDTO.getAdditional_photos() != null) {
            Set<ImageData> additionalImages = Arrays.stream(aboutUsDTO.getAdditional_photos()).map(photo -> {
                ImageData imageData = new ImageData();
                imageData.setFilename(photo.getOriginalFilename());
                try {
                    imageData.setContent(photo.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                imageData.setUser(userData);
                return imageData;
            }).collect(Collectors.toSet());
            aboutUs.setAdditionalPhoto(additionalImages);
        }
        aboutUs.setUser(userData);
        aboutUsRepository.save(aboutUs);

        return ResponseEntity.ok(new ResponseData(true, "Barcha ma'lumotlar saqlandi", null));

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
        if (aboutUs.isEmpty()) {
            return new ResponseEntity<>(new ResponseData(false, "Oldin birorta ham ma'lumot yaratilmagan", null), HttpStatus.BAD_REQUEST);
        }
        if (isLicense) {
            aboutUs.get().getLicensePhotos().add(imageData);
        } else {
            aboutUs.get().getAdditionalPhoto().add(imageData);
        }
        aboutUs.ifPresent(aboutUsRepository::save);
        return ResponseEntity.ok(new ResponseData(true, "Saqlandi", null));
    }

    public ResponseEntity<ResponseData> getAllInformation() {
        return ResponseEntity.ok(new ResponseData(true, "Barcha ma'lumotlar", aboutUsRepository.findAllInfo().stream().findFirst()));
    }

    public ResponseEntity<ResponseData> changeYouTubeLinks(String links){
        List<AboutUs> aboutUses = aboutUsRepository.findAll();
        if(aboutUses.size()==1){
            AboutUs aboutUs = aboutUses.get(0);
            aboutUs.setYouTubeLinks(links);
           /* if(!aboutUs.getYouTubeLinks().isEmpty()){
                aboutUs.setYouTubeLinks(aboutUs.getYouTubeLinks()+","+links);
            }
            else {
                aboutUs.setYouTubeLinks(links);
            }*/
            aboutUsRepository.save(aboutUs);
            return ResponseEntity.ok(new ResponseData(true, "Ma'lumotlar saqlandi", null));

        }
        else{
            return new ResponseEntity<>(new ResponseData(false, "Nimadir xato ketgan", null), HttpStatus.BAD_REQUEST);
        }
    }
}
