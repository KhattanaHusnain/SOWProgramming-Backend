package com.user.management.dto;

import jakarta.validation.constraints.NotBlank;

public record TopicRequest(

        @NotBlank
        String title,

        String content,
        String videoUrl,
        Integer durationInMinutes
) {}
