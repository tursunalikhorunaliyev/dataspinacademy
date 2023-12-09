package com.dataspin.dataspinacademy.service;

import com.dataspin.dataspinacademy.controller.StuffController;
import com.dataspin.dataspinacademy.controller.TagController;
import com.dataspin.dataspinacademy.dto.FileUploadDTO;
import com.dataspin.dataspinacademy.dto.OnlyNameRequest;
import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.entity.Stuff;
import com.dataspin.dataspinacademy.entity.Tag;
import com.dataspin.dataspinacademy.entity.UserData;
import com.dataspin.dataspinacademy.repository.CourseTypeRepository;
import com.dataspin.dataspinacademy.repository.StuffRepository;
import com.dataspin.dataspinacademy.repository.TagRepository;
import com.dataspin.dataspinacademy.security.JWTGenerator;
import lombok.AllArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.LinkedList;

@Service
@AllArgsConstructor
public class BaseService {

    private final JWTGenerator jwtGenerator;
    private final TagRepository tagRepository;
    private final StuffRepository stuffRepository;
    private final CourseTypeRepository courseTypeRepository;

    public ResponseEntity<ResponseData> create(OnlyNameRequest onlyNameRequest, HttpServletRequest request, Object instance) {
        final String name = onlyNameRequest.getName();
        final UserData user = jwtGenerator.getUserFromRequest(request);

        try {
            if (instance instanceof TagController) {
                final Tag tag = new Tag();
                tag.setName(name);
                tag.setUserData(user);
                try {
                    tagRepository.save(tag);
                    return ResponseEntity.ok(new ResponseData(true, "Ma'lumotlar saqlandi", null));
                } catch (DataIntegrityViolationException e) {
                    return new ResponseEntity<>(new ResponseData(false, "Allaqachon mavjud", null), HttpStatus.BAD_REQUEST);
                }
            } else if (instance instanceof StuffController) {
                final Stuff stuff = new Stuff();
                stuff.setName(name);
                stuff.setUserData(user);
                try {
                    stuffRepository.save(stuff);
                    return ResponseEntity.ok(new ResponseData(true, "Ma'lumotlar saqlandi", null));
                } catch (DataIntegrityViolationException e) {
                    return new ResponseEntity<>(new ResponseData(false, "Allaqachon mavjud", null), HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception e) {
            if (e.getMessage().contains("ConstraintViolationException")) {
                return new ResponseEntity<>(new ResponseData(false, "Bu nom mavjud!", "Error"), HttpStatus.BAD_REQUEST);
            }
        }

        return ResponseEntity.ok(new ResponseData(true, "Ma'lumotlar saqlandi.", "Succesfull"));
    }

    public ResponseEntity<ResponseData> fromExcel(FileUploadDTO file, HttpServletRequest request, Object instance) {
        byte[] decodedBytes = Base64.getDecoder().decode(file.getFile());
        ByteArrayInputStream in = new ByteArrayInputStream(decodedBytes);

        final UserData user = jwtGenerator.getUserFromRequest(request);

        try {
            final XSSFWorkbook xssfWorkbook = new XSSFWorkbook(in);
            final XSSFSheet sheet = xssfWorkbook.getSheetAt(0);

            if (instance instanceof TagController) {
                final LinkedList<Tag> tags = new LinkedList<>();

                for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
                    XSSFRow row = sheet.getRow(i);
                    final String cell = row.getCell(0).toString().trim();
                    if (!courseTypeRepository.existsByName(cell) && !cell.isEmpty()) {
                        final Tag tag = new Tag();
                        tag.setName(cell);
                        tag.setUserData(user);
                        tags.add(tag);
                    }
                }
                tagRepository.saveAll(tags);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(new ResponseData(true, "Ma'lumotlar saqlandi", null));
    }

    public ResponseEntity<ResponseData> getAllTags() {
        return ResponseEntity.ok(new ResponseData(true, "Barcha taglar", tagRepository.findAllTags()));
    }
}