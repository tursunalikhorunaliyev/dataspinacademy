package com.dataspin.dataspinacademy.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "reception_counter")
public class ReceptionCounter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private Integer totalCount;

    @Column(nullable = false)
    private Integer activeCount;

    @Column(nullable = false)
    private Integer inactiveCount;

}