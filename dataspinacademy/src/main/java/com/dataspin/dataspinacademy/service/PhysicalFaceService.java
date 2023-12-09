package com.dataspin.dataspinacademy.service;

import com.dataspin.dataspinacademy.dto.PhysicalFaceDTO;
import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.entity.PhysicalFace;
import com.dataspin.dataspinacademy.repository.PhysicalFaceRepository;
import com.dataspin.dataspinacademy.security.JWTGenerator;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class PhysicalFaceService {

    private final JWTGenerator jwtGenerator;
    private final PhysicalFaceRepository physicalFaceRepository;

    public ResponseEntity<ResponseData> create(PhysicalFaceDTO physicalFaceDTO, HttpServletRequest request){
        PhysicalFace physicalFace = new PhysicalFace();
        physicalFace.setFirstname(physicalFaceDTO.getFirstname());
        physicalFace.setLastname(physicalFaceDTO.getLastname());
        physicalFace.setMiddlename(physicalFaceDTO.getMiddlename());
        physicalFace.setPassport(physicalFaceDTO.getPassport());

        DateTimeFormatter birthdateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate birthdate = LocalDate.parse(physicalFaceDTO.getBirthday(), birthdateFormatter);
        physicalFace.setBirthday(birthdate);
        physicalFace.setTel1(physicalFaceDTO.getTel1());
        physicalFace.setTel2(physicalFaceDTO.getTel2());
        physicalFace.setUser(jwtGenerator.getUserFromRequest(request));
        try {
            physicalFaceRepository.save(physicalFace);
            return ResponseEntity.ok(new ResponseData(true, "Ma'lumotlar saqlandi", null));
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(new ResponseData(false, "Allaqachon mavjud", null), HttpStatus.BAD_REQUEST);
        }
    }
    public ResponseEntity<ResponseData> getAll(){
        return ResponseEntity.ok(new ResponseData(true, "Barcha shaxslar", physicalFaceRepository.findAllFace()));
    }
}
