package com.user.management.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "course_progress")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne private User user;
    @ManyToOne private Course course;

    private Double progress;
    private Boolean completed;
}

