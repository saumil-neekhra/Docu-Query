package com.docuquery.dto;

import com.docuquery.entity.DocumentStatus;
import java.time.LocalDateTime;

public record DocumentResponse(
        Long id,
        String originalFilename,
        LocalDateTime uploadedAt,
        DocumentStatus status
) {}