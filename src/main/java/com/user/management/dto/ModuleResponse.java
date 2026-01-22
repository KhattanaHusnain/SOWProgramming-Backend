package com.user.management.dto;

import java.util.List;

public record ModuleResponse(

        Long id,
        String title,
        String description,
        Integer orderIndex,

        Long courseId
) {}
