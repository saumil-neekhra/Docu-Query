package com.docuquery.dto;

import jakarta.validation.constraints.NotBlank;

public record QuestionRequest(
        @NotBlank(message = "Question cannot be empty")
        String question) {}