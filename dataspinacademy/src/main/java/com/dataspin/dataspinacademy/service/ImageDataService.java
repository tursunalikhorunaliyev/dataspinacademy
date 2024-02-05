package com.dataspin.dataspinacademy.service;

import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.entity.ImageData;
import com.dataspin.dataspinacademy.entity.UserData;
import com.dataspin.dataspinacademy.projection.ImageDataInfo;
import com.dataspin.dataspinacademy.repository.ImageDataRepository;
import com.dataspin.dataspinacademy.security.JWTGenerator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ImageDataService {

    private final ImageDataRepository imageDataRepository;

    private final JWTGenerator jwtGenerator;



    public ResponseEntity<byte[]> getImage(long id) {
        final ImageData image = imageDataRepository.findById(id).orElse(null);
        if (image != null) {
            final HttpHeaders headers = new HttpHeaders();
            final String filename = image.getFilename();
            String ext = filename.substring(filename.lastIndexOf("."));
            //headers.add("Content-Disposition", "inline;filename=" + filename);
            headers.setContentType((ext.contains("png")) ? MediaType.IMAGE_PNG : (ext.contains("pdf"))? MediaType.APPLICATION_PDF: MediaType.IMAGE_JPEG);
            return ResponseEntity.ok().headers(headers).body(image.getContent());
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    public ResponseEntity<ResponseData> getAll(HttpServletRequest request){
        if(jwtGenerator.isAdmin(jwtGenerator.getUserFromRequest(request))){
            List<String> getAllInfo = imageDataRepository.getAllImages().stream().map(e->"http://dataspinacademy.uz/api/image/?id="+e.getId()).toList();

            return ResponseEntity.ok(new ResponseData(true, "Images", getAllInfo));
        }
        else{
            return new ResponseEntity<>(new ResponseData(false, "Siz admin emassiz", null), HttpStatus.BAD_REQUEST);
        }
    }
    public ResponseEntity<ResponseData> uploadPicture(MultipartFile photo, HttpServletRequest request) throws IOException {
        UserData userData = jwtGenerator.getUserFromRequest(request);
        if(jwtGenerator.isAdmin(userData)){
            ImageData imageData = new ImageData();
            imageData.setFilename(photo.getOriginalFilename());
            imageData.setContent(photo.getBytes());
            imageData.setUser(userData);
            imageDataRepository.save(imageData);


            return ResponseEntity.ok(new ResponseData(true, "Images", "Saqlandi"));
        }
        else{
            return new ResponseEntity<>(new ResponseData(false, "Siz admin emassiz", null), HttpStatus.BAD_REQUEST);
        }
    }
}
