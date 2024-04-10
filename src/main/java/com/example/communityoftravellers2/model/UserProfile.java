package com.example.communityoftravellers2.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class UserProfile extends AuditBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String occupation;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String biography;
    @Column(nullable = false)
    private String favoriteQoute;
    @Column(nullable = false)
    private String expertise;

    @OneToOne
    private User user;
}
