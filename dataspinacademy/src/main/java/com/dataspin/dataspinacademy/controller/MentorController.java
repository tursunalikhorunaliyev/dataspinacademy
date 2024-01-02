package com.dataspin.dataspinacademy.controller;

import com.dataspin.dataspinacademy.dto.MentorDTO;
import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.service.MentorService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("api/mentors")
@AllArgsConstructor
public class MentorController {
    private final MentorService mentorService;

    @PostMapping("/new")
    public ResponseEntity<ResponseData> create(@RequestBody @Valid MentorDTO mentorDTO, HttpServletRequest request) {
        return mentorService.create(mentorDTO, request);
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseData> update(@RequestParam("id") Long id, @RequestParam("courseIDs") String courseIDs, @Nullable @RequestParam("subMentors") String subMentors, HttpServletRequest request) {
        return mentorService.update(id, courseIDs, subMentors, request);
    }

    @GetMapping("/")
    public ResponseEntity<ResponseData> getAll() {
        return mentorService.getAll();
    }

}
