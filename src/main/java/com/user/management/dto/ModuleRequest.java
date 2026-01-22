package com.user.management.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ModuleRequest(

        @NotBlank
        String title,

        String description,

        @NotNull
        Integer orderIndex
) {}
