package com.dataspin.dataspinacademy.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "promocode")
public class Promocode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id")
    private UserInfo userInfo;

    @Column(nullable = false, unique = true, length = 7)
    private String promoCode;

    @Column(nullable = false)
    private Integer totalCount;

    @Column(nullable = false)
    private Integer activeCount;

    @Column(nullable = false)
    private Boolean status;

    @CreationTimestamp
    private Timestamp date;

    @ManyToMany
    @JoinTable(name = "subscribed_users", joinColumns = @JoinColumn(name = "promocode_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<UserInfo> subscribedUsers;



}