
package com.docuquery.dto;

import java.util.List;

public record QAResponse(
        String answer,
        List<SourceCitation> sources
) {}
