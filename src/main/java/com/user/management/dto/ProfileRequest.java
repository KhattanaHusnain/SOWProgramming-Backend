package com.user.management.dto;

import jakarta.validation.constraints.*;

public record ProfileRequest(

        @NotBlank(message = "First name is required")
        @Size(max = 50)
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(max = 50)
        String lastName,

        @Pattern(
                regexp = "^\\+?[0-9]{10,15}$",
                message = "Phone number must be valid"
        )
        String phone,

        @Size(max = 500)
        String bio,

        @NotNull(message = "Role is required")
        String role,

        String skillLevel,

        @Size(max = 255)
        String interests,

        @Pattern(
                regexp = "^(http|https)://.*$",
                message = "Profile image must be a valid URL"
        )
        String profileImage,

        String githubUrl,
        String linkedinUrl,
        String websiteUrl,

        @Size(max = 10)
        String preferredLanguage,

        @Size(max = 50)
        String timezone
) {}
