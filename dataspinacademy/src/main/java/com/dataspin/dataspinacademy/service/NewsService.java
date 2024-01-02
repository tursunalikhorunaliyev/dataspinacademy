package com.dataspin.dataspinacademy.service;

import com.dataspin.dataspinacademy.dto.NewsDTO;
import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.entity.ImageData;
import com.dataspin.dataspinacademy.entity.News;
import com.dataspin.dataspinacademy.entity.UserData;
import com.dataspin.dataspinacademy.repository.ImageDataRepository;
import com.dataspin.dataspinacademy.repository.NewsRepository;
import com.dataspin.dataspinacademy.security.JWTGenerator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NewsService {
    private final JWTGenerator jwtGenerator;

    private final NewsRepository newsRepository;
    private final ImageDataRepository imageDataRepository;

    public ResponseEntity<ResponseData> create(NewsDTO newsDTO, HttpServletRequest request) throws IOException {
        UserData userData = jwtGenerator.getUserFromRequest(request);
        final boolean isAdmin = jwtGenerator.isAdmin(userData);
        if (!isAdmin) {
            return new ResponseEntity<>(new ResponseData(false, "Ushbu resursga murojaat qilish huquqingiz yo'q", null), HttpStatus.BAD_REQUEST);
        }
        News news = new News();
        news.setName(newsDTO.getName());
        news.setShortDesc(newsDTO.getShortDesc());
        news.setFullDesc(newsDTO.getFullDesc());
        ImageData imageData = new ImageData();
        imageData.setFilename(newsDTO.getPhoto().getOriginalFilename());
        imageData.setContent(newsDTO.getPhoto().getBytes());
        imageData.setUser(userData);
        news.setPhoto(imageData);
        news.setUser(userData);
        newsRepository.save(news);
        return ResponseEntity.ok(new ResponseData(true, "Ma'lumotlar saqlandi", null));
    }

    public ResponseEntity<ResponseData> update(Long id, NewsDTO newsDTO, HttpServletRequest request) throws IOException {
        UserData userData = jwtGenerator.getUserFromRequest(request);
        final boolean isAdmin = jwtGenerator.isAdmin(userData);
        final Optional<News> oldNews = newsRepository.findById(id);
        if (oldNews.isEmpty()) {
            return new ResponseEntity<>(new ResponseData(false, "Yangilik topilmadi", null), HttpStatus.BAD_REQUEST);
        }
        if (!isAdmin) {
            return new ResponseEntity<>(new ResponseData(false, "Ushbu resursga murojaat qilish huquqingiz yo'q", null), HttpStatus.BAD_REQUEST);
        }
        newsRepository.deleteManually(id);
        imageDataRepository.deleteByIds(Collections.singletonList(oldNews.get().getPhoto().getId()));
        News news = new News();
        news.setName(newsDTO.getName());
        news.setShortDesc(newsDTO.getShortDesc());
        news.setFullDesc(newsDTO.getFullDesc());
        ImageData imageData = new ImageData();
        imageData.setFilename(newsDTO.getPhoto().getOriginalFilename());
        imageData.setContent(newsDTO.getPhoto().getBytes());
        imageData.setUser(userData);
        news.setPhoto(imageData);
        news.setUser(userData);
        newsRepository.save(news);
        return ResponseEntity.ok(new ResponseData(true, "Ma'lumotlar yangilandi", null));
    }

    public ResponseEntity<ResponseData> delete(Long id, HttpServletRequest request) {
        UserData userData = jwtGenerator.getUserFromRequest(request);
        final boolean isAdmin = jwtGenerator.isAdmin(userData);
        final Optional<News> oldNews = newsRepository.findById(id);
        if (oldNews.isEmpty()) {
            return new ResponseEntity<>(new ResponseData(false, "Yangilik topilmadi", null), HttpStatus.BAD_REQUEST);
        }
        if (!isAdmin) {
            return new ResponseEntity<>(new ResponseData(false, "Ushbu resursga murojaat qilish huquqingiz yo'q", null), HttpStatus.BAD_REQUEST);
        }
        newsRepository.deleteManually(id);
        imageDataRepository.deleteByIds(Collections.singletonList(oldNews.get().getPhoto().getId()));
        return ResponseEntity.ok(new ResponseData(true, "Ma'lumotlar o'chirildi", null));
    }

    public ResponseEntity<ResponseData> getAll() {
        return ResponseEntity.ok(new ResponseData(true, "Barcha yangiliklar", newsRepository.findAllStuff()));
    }
}
