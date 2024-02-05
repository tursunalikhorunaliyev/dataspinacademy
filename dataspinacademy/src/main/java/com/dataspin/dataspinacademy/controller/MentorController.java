package com.dataspin.dataspinacademy.controller;

import com.dataspin.dataspinacademy.dto.MentorDTO;
import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.service.MentorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

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
    public ResponseEntity<ResponseData> update(@RequestParam("id") Long id, @RequestParam("courseIDs") String courseIDs, @Nullable @RequestParam("subMentors") String subMentors, @RequestParam("youtube_links") String youtubeLinks, HttpServletRequest request) {
        return mentorService.update(id, courseIDs, subMentors, youtubeLinks, request);
    }

    @GetMapping("/")
    public ResponseEntity<ResponseData> getAll() {
        return mentorService.getAll();
    }

    @PostMapping("/upload-cv")
    public ResponseEntity<ResponseData> uploadCV(@RequestParam("cv") MultipartFile cv, @RequestParam("mentor") Long mentorID, HttpServletRequest request) throws IOException {
       return mentorService.uploadCV(cv, mentorID, request);
    }


}
