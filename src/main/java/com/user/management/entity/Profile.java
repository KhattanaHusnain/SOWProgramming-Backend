package com.user.management.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {

    @Id
    private Long id; // SAME AS user.id

    /* ------------------ Basic Info ------------------ */

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(length = 20)
    private String phone;

    @Column(length = 255)
    private String profileImage; // URL

    @Column(length = 500)
    private String bio;

    /* ------------------ E-Learning Context ------------------ */

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role; // STUDENT / INSTRUCTOR / ADMIN

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private SkillLevel skillLevel; // BEGINNER / INTERMEDIATE / ADVANCED

    @Column(length = 255)
    private String interests; // e.g. "Spring Boot, Android, DSA"

    /* ------------------ Stats ------------------ */

    @Column(nullable = false)
    private Integer totalCoursesEnrolled = 0;

    @Column(nullable = false)
    private Integer totalCoursesCompleted = 0;

    /* ------------------ Social Links ------------------ */

    @Column(length = 255)
    private String githubUrl;

    @Column(length = 255)
    private String linkedinUrl;

    @Column(length = 255)
    private String websiteUrl;

    /* ------------------ Preferences ------------------ */

    @Column(length = 10)
    private String preferredLanguage; // en, ur, etc.

    @Column(length = 50)
    private String timezone;

    /* ------------------ Relationship ------------------ */

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "id")
    private User user;
}
