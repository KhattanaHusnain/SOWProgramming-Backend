package com.user.management.dto;

public record TopicResponse(

        Long id,
        String title,
        String content,
        String videoUrl,
        Integer durationInMinutes,

        Long moduleId
) {}
