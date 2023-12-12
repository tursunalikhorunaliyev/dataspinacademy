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
@Table(name = "user_info", uniqueConstraints = @UniqueConstraint(name = "unique_user", columnNames = {"firstname", "lastname", "middlename"}))
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column
    private String middlename;

    private LocalDate birthday;

    @Column(nullable = false, unique = true)
    private String primaryPhone;

    @Column
    private String secondaryPhone;

    @Column(unique = true)
    private String telegramUsername;

    @OneToOne
    @JoinColumn(name = "user", referencedColumnName = "id")
    private UserData userData;

    @CreationTimestamp
    private Timestamp date;

}