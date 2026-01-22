package com.user.management.dto;

public record ProfileRequest(

        String firstName,
        String lastName,
        String phone,
        String profileImage,
        String bio,

        String role,          // only on create
        String skillLevel,
        String interests,

        String githubUrl,
        String linkedinUrl,
        String websiteUrl,

        String preferredLanguage,
        String timezone
) {}
