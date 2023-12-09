package com.dataspin.dataspinacademy.service;

import com.dataspin.dataspinacademy.entity.ImageData;
import com.dataspin.dataspinacademy.repository.ImageDataRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

@Service
public class ImageDataService {

    private final ImageDataRepository imageDataRepository;

    public ImageDataService(ImageDataRepository imageDataRepository) {
        this.imageDataRepository = imageDataRepository;
    }

    public ResponseEntity<byte[]> getImage(long id) {
        final ImageData image = imageDataRepository.findById(id).orElse(null);
        if (image != null) {
            final HttpHeaders headers = new HttpHeaders();
            final String filename = image.getFilename();
            String ext = filename.substring(filename.lastIndexOf("."));
            //headers.add("Content-Disposition", "inline;filename=" + filename);
            headers.setContentType((ext.contains("png")) ? MediaType.IMAGE_PNG : MediaType.IMAGE_JPEG);
            return ResponseEntity.ok().headers(headers).body(image.getContent());
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
