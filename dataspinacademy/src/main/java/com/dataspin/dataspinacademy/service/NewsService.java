package com.dataspin.dataspinacademy.service;
import com.dataspin.dataspinacademy.dto.NewsDTO;
import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.entity.ImageData;
import com.dataspin.dataspinacademy.entity.News;
import com.dataspin.dataspinacademy.entity.UserData;
import com.dataspin.dataspinacademy.repository.NewsRepository;
import com.dataspin.dataspinacademy.security.JWTGenerator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Service
@AllArgsConstructor
public class NewsService {
    private final JWTGenerator jwtGenerator;

    private final NewsRepository newsRepository;

    public ResponseEntity<ResponseData> create(NewsDTO newsDTO, HttpServletRequest request) throws IOException {
        UserData userData = jwtGenerator.getUserFromRequest(request);
        final boolean isAdmin = jwtGenerator.isAdmin(userData);
        if(!isAdmin){
            return new ResponseEntity<>(new ResponseData(false, "Ushbu resursga murojaat qilish huquqingiz yo'q", null), HttpStatus.BAD_REQUEST);
        }
        News news = new News();
        news.setName(news.getName());
        news.setShortDesc(news.getShortDesc());
        news.setFullDesc(news.getFullDesc());
        ImageData imageData = new ImageData();
        imageData.setFilename(newsDTO.getPhoto().getOriginalFilename());
        imageData.setContent(newsDTO.getPhoto().getBytes());
        imageData.setUser(userData);
        news.setPhoto(imageData);
        newsRepository.save(news);
        return ResponseEntity.ok(new ResponseData(true,"Ma'lumotlar saqlandi", null));
    }
    public ResponseEntity<ResponseData> getAll(){
        return ResponseEntity.ok(new ResponseData(true, "Barcha yangiliklar", newsRepository.findAllStuff()));
    }
}
