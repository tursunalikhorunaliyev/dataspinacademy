package com.dataspin.dataspinacademy.service;

import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.entity.*;
import com.dataspin.dataspinacademy.repository.CourseReplyRepository;
import com.dataspin.dataspinacademy.repository.CourseRepository;
import com.dataspin.dataspinacademy.repository.DeletedCommentRepository;
import com.dataspin.dataspinacademy.repository.UserInfoRepository;
import com.dataspin.dataspinacademy.security.JWTGenerator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {

    private final CourseRepository courseRepository;
    private final JWTGenerator jwtGenerator;
    private final UserInfoRepository userInfoRepository;
    private final CourseReplyRepository courseReplyRepository;
    private final DeletedCommentRepository deletedCommentRepository;

    public ResponseEntity<ResponseData> reply(Long courseId, String commentText, HttpServletRequest request) {
        UserData userData = jwtGenerator.getUserFromRequest(request);
        Optional<UserInfo> userInfo = userInfoRepository.findByUserData(userData);
        if (userInfo.isEmpty()) {
            return new ResponseEntity<>(new ResponseData(false, "Ushbu user ro'yxatdan o'tmagan", null), HttpStatus.BAD_REQUEST);
        }

        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isEmpty()) {
            return new ResponseEntity<>(new ResponseData(false, "Kurs topilmadi", null), HttpStatus.BAD_REQUEST);
        }
        CourseReply courseReply = new CourseReply();
        courseReply.setCourse(course.get());
        courseReply.setReplyText(commentText);
        courseReply.setUserInfo(userInfo.get());
        courseReplyRepository.save(courseReply);
        return ResponseEntity.ok(new ResponseData(true, "Comment saqlandi", null));
    }

    public ResponseEntity<ResponseData> deleteComment(Long commentId) {
        Optional<CourseReply> courseReply = courseReplyRepository.findById(commentId);
        if (courseReply.isEmpty()) {
            return new ResponseEntity<>(new ResponseData(false, "Bunday comment topilmadi", null), HttpStatus.BAD_REQUEST);
        }
        CourseReply courseReply1 = courseReply.get();
        DeletedComment deletedComment = new DeletedComment();
        deletedComment.setCourse(courseReply1.getCourse());
        deletedComment.setReplyText(courseReply1.getReplyText());
        deletedComment.setUserInfo(courseReply1.getUserInfo());
        deletedComment.setCommentedAt(courseReply1.getDate().toString());
        deletedCommentRepository.save(deletedComment);
        courseReplyRepository.deleteById(commentId);
        return ResponseEntity.ok(new ResponseData(true, "Comment o'chirib yuborildi", null));

    }

    public ResponseEntity<ResponseData> getAllCommentsByCourse(Long courseId) {
        return ResponseEntity.ok(new ResponseData(true, "Barcha komentariyalar", courseReplyRepository.findByCourse_Id(courseId)));
    }

    public ResponseEntity<ResponseData> getAllDeletedCommentsById(Long courseId, HttpServletRequest request) {
        UserData userData = jwtGenerator.getUserFromRequest(request);

        if(!jwtGenerator.isAdmin(userData)){
            return new ResponseEntity<>(new ResponseData(false,"Ushbu resursga murojaat qilish huquqingiz mavjud emas",null), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new ResponseData(true, "Barcha o'chirib yuborilgan komentariyalar", deletedCommentRepository.findByCourse_Id(courseId)));
    }

    public ResponseEntity<ResponseData> getAllCommentsByCourseAndUser(Long courseId, Long userInfoId, HttpServletRequest request) {
        UserData userData = jwtGenerator.getUserFromRequest(request);

        if(!jwtGenerator.isAdmin(userData)){
            return new ResponseEntity<>(new ResponseData(false,"Ushbu resursga murojaat qilish huquqingiz mavjud emas",null), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new ResponseData(true, "Userning barcha comentariyalari", courseReplyRepository.findByCourse_IdAndUserInfo_Id(courseId, userInfoId)));

    }

    public ResponseEntity<ResponseData> getAllDeletedCommentsByCourseAndUser(Long courseId, Long userInfoId, HttpServletRequest request) {
        UserData userData = jwtGenerator.getUserFromRequest(request);

        if(!jwtGenerator.isAdmin(userData)){
            return new ResponseEntity<>(new ResponseData(false,"Ushbu resursga murojaat qilish huquqingiz mavjud emas",null), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new ResponseData(true, "Userning o'chirib yuborilgan barcha komentariyalari", deletedCommentRepository.findByCourse_IdAndUserInfo_Id(courseId, userInfoId)));
    }
}
