package com.dataspin.dataspinacademy.controller;
import com.dataspin.dataspinacademy.dto.NewsDTO;
import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.service.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("api/news")
@AllArgsConstructor
public class NewsController {
    private final NewsService newsService;
    @PostMapping("/new")
    public ResponseEntity<ResponseData> create(@ModelAttribute @Valid NewsDTO newsDTO, HttpServletRequest request) throws IOException {
        return newsService.create(newsDTO, request);
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseData> update(@ModelAttribute @Valid NewsDTO newsDTO, @RequestParam("id") Long id, HttpServletRequest request) throws IOException {
        return newsService.update(id, newsDTO, request);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseData> update(@RequestParam("id") Long id, HttpServletRequest request) {
        return newsService.delete(id, request);
    }

    @GetMapping("/")
    public ResponseEntity<ResponseData> getAll(){
        return newsService.getAll();
    }
}
