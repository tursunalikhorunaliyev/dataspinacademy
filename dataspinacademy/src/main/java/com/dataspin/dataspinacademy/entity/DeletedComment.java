package com.dataspin.dataspinacademy.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "deleted_comment", indexes = {
        @Index(name = "idx_deletedcomment_course_id", columnList = "course_id")
})
public class DeletedComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @Column(nullable = false, length = 2000)
    private String replyText;

    @Column(nullable = false)
    private String commentedAt;

    @CreationTimestamp
    private Timestamp deletedAt;

    @ManyToOne
    @JoinColumn(name = "user_info", referencedColumnName = "id")
    private UserInfo userInfo;

}