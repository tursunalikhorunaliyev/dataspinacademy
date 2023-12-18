package com.dataspin.dataspinacademy.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "about_us")
public class AboutUs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String academyName;

    @Column(nullable = false)
    private String activityDesc;

    @Column(nullable = false)
    private String fullAboutUs;

    private String youTubeLinks;

    @Column(nullable = false, unique = true)
    private String ourLocation;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "main_photo", referencedColumnName = "id", nullable = false)
    private ImageData mainPhoto;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "license_photos", joinColumns = @JoinColumn(name = "about_us", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "photo_id", referencedColumnName = "id", nullable = false))
    Set<ImageData> licensePhotos;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "additional_photo", joinColumns = @JoinColumn(name = "about_us", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "photo_id", referencedColumnName = "id"))
    Set<ImageData> additionalPhoto;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserData user;




}