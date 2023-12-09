package com.dataspin.dataspinacademy.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "phone_codes", indexes = {
        @Index(name = "idx_phonecode_phone", columnList = "phone")
})
public class PhoneCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String phone;

    @Column(unique = true, nullable = false)
    private String code;

    @CreationTimestamp
    private Timestamp date;

}