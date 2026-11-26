package com.docuquery.dto;

public record SourceCitation(
        String filename,
        Integer pageNumber,
        String excerpt
) {}
