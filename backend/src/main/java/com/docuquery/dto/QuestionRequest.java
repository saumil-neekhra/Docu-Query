package com.docuquery.dto;

import jakarta.validation.constraints.NotBlank;

public record QuestionRequest(
        @NotBlank(message = "Question cannit be empty")
        String question) {}