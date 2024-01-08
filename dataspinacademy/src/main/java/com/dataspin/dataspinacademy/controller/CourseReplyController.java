package com.dataspin.dataspinacademy.controller;

import com.dataspin.dataspinacademy.dto.ResponseData;
import com.dataspin.dataspinacademy.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/comments")
@AllArgsConstructor
public class CourseReplyController {
    private final CommentService commentService;
    @PostMapping("/reply")
    public ResponseEntity<ResponseData> reply(@RequestParam("course_id") Long courseId, @RequestParam("text") String text, HttpServletRequest request){
        return commentService.reply(courseId, text, request);
    }
    @GetMapping("/")
    public ResponseEntity<ResponseData> allComments(@RequestParam Long course_id, @RequestParam(required = false) Long user_info_id, HttpServletRequest request){
        if(user_info_id!=null){
            return commentService.getAllCommentsByCourseAndUser(course_id, user_info_id, request);
        }
        else{
            return commentService.getAllCommentsByCourse(course_id);
        }
    }
    @GetMapping("/delete")
    public ResponseEntity<ResponseData> allDeletedComments(@RequestParam Long course_id, @RequestParam(required = false) Long user_info_id, HttpServletRequest request){
        if(user_info_id!=null){
            return commentService.getAllDeletedCommentsByCourseAndUser(course_id, user_info_id, request);
        }
        else{
            return commentService.getAllCommentsByCourse(course_id);
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseData> deleteReply(@RequestParam("comment_id") Long comment_id){
        return commentService.deleteComment(comment_id);
    }


}
