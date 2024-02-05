package com.dataspin.dataspinacademy.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "reception")
public class Reception {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String receptionNumber;


    @Column(nullable = false)
    private String courseName;

    @ManyToOne
    @JoinColumn(name = "course_for", referencedColumnName = "id")
    private CourseFor courseFor;
    @ManyToOne
    @JoinColumn(name = "course_type", referencedColumnName = "id")
    private CourseType courseType;

    @ManyToOne
    @JoinColumn(name = "course_photo", referencedColumnName = "id")
    private ImageData coursePhoto;

    private String description;
    private String promoCode;

    @CreationTimestamp
    private Timestamp date;

    @ManyToOne
    @JoinColumn(name = "mentor", referencedColumnName = "id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "user_info", referencedColumnName = "id", nullable = false)
    private UserInfo userInfo;

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
    private UserData user;
}