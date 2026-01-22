package com.user.management.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* ---------------- Basic Info ---------------- */

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, length = 500)
    private String shortDescription;

    @Column(length = 5000)
    private String description;

    @Column(length = 255)
    private String thumbnailUrl;

    @Column(nullable = false)
    private Boolean published = false;

    /* ---------------- Meta ---------------- */

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CourseLevel level; // BEGINNER, INTERMEDIATE, ADVANCED

    @Column(nullable = false)
    private Integer durationInHours;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String language;

    /* ---------------- Relations ---------------- */

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Module> modules;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quiz> quizzes;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Assignment> assignments;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EnrolledCourse> enrollments;

    /* ---------------- Audit ---------------- */

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

