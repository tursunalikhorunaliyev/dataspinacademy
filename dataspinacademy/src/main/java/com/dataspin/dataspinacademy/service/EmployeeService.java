package com.dataspin.dataspinacademy.service;

import com.dataspin.dataspinacademy.dto.EmployeeDTO;
import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.entity.Employee;
import com.dataspin.dataspinacademy.entity.ImageData;
import com.dataspin.dataspinacademy.entity.UserData;
import com.dataspin.dataspinacademy.repository.EmployeeRepository;
import com.dataspin.dataspinacademy.repository.PhysicalFaceRepository;
import com.dataspin.dataspinacademy.repository.StuffRepository;
import com.dataspin.dataspinacademy.security.JWTGenerator;
import lombok.AllArgsConstructor;
import org.apache.catalina.mbeans.UserMBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PhysicalFaceRepository physicalFaceRepository;
    private final StuffRepository stuffRepository;
    private final JWTGenerator jwtGenerator;


    public ResponseEntity<ResponseData> create(EmployeeDTO employeeDTO, HttpServletRequest request) throws IOException {
        UserData userData = jwtGenerator.getUserFromRequest(request);
        Employee employee = new Employee();
        employee.setFace(physicalFaceRepository.findById(employeeDTO.getFaceId()).get());
        employee.setStuff(stuffRepository.findById(employeeDTO.getStuffId()).get());
        employee.setPractice(employeeDTO.getPractice());
        employee.setIsVerified(employeeDTO.getIsVerified());

        ImageData imageData = new ImageData();
        imageData.setFilename(employeeDTO.getPhoto().getOriginalFilename());
        imageData.setContent(employeeDTO.getPhoto().getBytes());
        imageData.setUser(userData);

        employee.setPhoto(imageData);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime startDateTime = LocalDateTime.parse(employeeDTO.getStartDate(), dateTimeFormatter);
        employee.setStartDate(startDateTime);

        if(employeeDTO.getEndDate() != null){
            LocalDateTime endDateTime = LocalDateTime.parse(employeeDTO.getEndDate(), dateTimeFormatter);
            employee.setEndDate(endDateTime);
        }
        employee.setUser(userData);

        try {
            employeeRepository.save(employee);
            return ResponseEntity.ok(new ResponseData(true, "Ma'lumotlar saqlandi", null));
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(new ResponseData(false, "Allaqachon mavjud", null), HttpStatus.BAD_REQUEST);
        }

    }
    public ResponseEntity<ResponseData> getAll(){
        return ResponseEntity.ok(new ResponseData(true, "Barcha hodimlar", employeeRepository.findAllInfo()));
    }
}
