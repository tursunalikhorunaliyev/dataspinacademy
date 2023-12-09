package com.dataspin.dataspinacademy.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "physical_face", uniqueConstraints = @UniqueConstraint(columnNames = {"passport", "tel1"}))
public class PhysicalFace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String firstname;
    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false)
    private String middlename;

    @Column(unique = true, nullable = false, length = 9)
    private String passport;

    private LocalDate birthday;
    @Column(nullable = false, unique = true)
    private String tel1;

    private String tel2;

    @CreationTimestamp
    private Timestamp date;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
    private UserData user;

}