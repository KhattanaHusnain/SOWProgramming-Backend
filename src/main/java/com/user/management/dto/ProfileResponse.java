package com.user.management.dto;

public record ProfileResponse(

        /* -------- Identity -------- */
        Long id,
        Long userId,
        String username,
        String email,

        /* -------- Basic Info -------- */
        String firstName,
        String lastName,
        String phone,
        String profileImage,
        String bio,

        /* -------- E-Learning Context -------- */
        String role,
        String skillLevel,
        String interests,

        /* -------- Platform Stats -------- */
        Integer totalCoursesEnrolled,
        Integer totalCoursesCompleted,

        /* -------- Social -------- */
        String githubUrl,
        String linkedinUrl,
        String websiteUrl,

        /* -------- Preferences -------- */
        String preferredLanguage,
        String timezone
) {}
