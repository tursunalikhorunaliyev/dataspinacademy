package com.dataspin.dataspinacademy.service;
import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.entity.CourseType;
import com.dataspin.dataspinacademy.entity.ImageData;
import com.dataspin.dataspinacademy.entity.UserData;
import com.dataspin.dataspinacademy.repository.CourseTypeRepository;
import com.dataspin.dataspinacademy.repository.TagRepository;
import com.dataspin.dataspinacademy.security.JWTGenerator;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseTypeService {

    private final JWTGenerator jwtGenerator;
    private final CourseTypeRepository courseTypeRepository;
    private final TagRepository tagRepository;

    public ResponseEntity<ResponseData>  create(String name, String description,  String courseTags, MultipartFile photo, HttpServletRequest request) throws IOException {
        UserData userData = jwtGenerator.getUserFromRequest(request);
        CourseType courseType = new CourseType();
        courseType.setName(name);
        courseType.setDescription(description);
        ImageData imageData = new ImageData();
        imageData.setContent(photo.getBytes());
        imageData.setFilename(photo.getOriginalFilename());
        imageData.setUser(userData);
        courseType.setPhoto(imageData);
        courseType.setUser(userData);
        courseType.setCourseTags(new HashSet<>(tagRepository.getByInIds(Arrays.stream(courseTags.split(",")).map(Long::parseLong).toList())));
        courseTypeRepository.save(courseType);
        try {
            courseTypeRepository.save(courseType);
            return ResponseEntity.ok(new ResponseData(true, "Ma'lumotlar saqlandi", null));
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity(new ResponseData(false, "Allaqachon mavjud", null), HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<ResponseData> getAll(){
        return ResponseEntity.ok(new ResponseData(true, "Successful", courseTypeRepository.findAllInfo()));
    }
}
