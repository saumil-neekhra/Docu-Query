package com.docuquery.controller;

import com.docuquery.dto.DocumentResponse;
import com.docuquery.dto.QuestionRequest;
import com.docuquery.entity.DocumentEntity;
import com.docuquery.entity.DocumentStatus;
import com.docuquery.rag.generation.DocumentQAService;
import com.docuquery.rag.ingestion.DocumentIngestionService;
import com.docuquery.rag.retrieval.DocumentRetrievalService;
import com.docuquery.repository.DocumentRepository;
import com.docuquery.service.DocumentEntityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentIngestionService ingestionService;
    private final DocumentRetrievalService retrievalService;
    private final DocumentQAService qaService;
    private final DocumentRepository documentRepository;
    private final DocumentEntityService documentEntityService;

//    For Checking Connection
    @GetMapping("")
    public ResponseEntity<List<DocumentResponse>> getAllDocuments(){                // Apply Pagination and filtering both failed and completed are showing
        List<DocumentResponse> documents = documentRepository
                .findAll(Sort.by(Sort.Direction.DESC, "uploadedAt"))
                .stream()
                .map(document -> new DocumentResponse(
                        document.getId(),
                        document.getOriginalFilename(),
                        document.getUploadedAt(),
                        document.getStatus()
                ))
                .toList();
        return ResponseEntity.ok(documents);
    }

//    For uploading document and creating vector embeddings in vectorStore DB.
    @PostMapping("/upload")
    public ResponseEntity<?> uploadDocument(
        @RequestParam("file") MultipartFile file) throws IOException {

        // Check whether a file was provided.
        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Please upload a non-empty PDF file"
            );
        }
        // Check the file extension.
        String filename = file.getOriginalFilename();

        if (filename == null ||
                !filename.toLowerCase().endsWith(".pdf")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Only PDF files are allowed"
            );
        }

    // 1. Create and save document metadata
    DocumentEntity document =
            new DocumentEntity(file.getOriginalFilename());
    document = documentRepository.save(document);

    try {
        // 2. Use the generated database ID for RAG ingestion
        ingestionService.ingestDocument(file, document.getId());

        // 3. Mark document as successfully processed
        document.setStatus(DocumentStatus.COMPLETED);
        documentRepository.save(document);

        return ResponseEntity.ok(Map.of(
                "documentId", document.getId(),
                "filename", document.getOriginalFilename(),
                "status", document.getStatus(),
                "message", "Document uploaded and indexed successfully"
        ));

    } catch (Exception e) {

        // 4. Mark document as failed if ingestion fails
        document.setStatus(DocumentStatus.FAILED);
        documentRepository.save(document);

        throw new RuntimeException(
                "Document ingestion failed: " + e.getMessage(), e);
    }
}

//    For Getting Retrieved Context From the Modal
    @GetMapping("/{documentId}")
    public List<Document> retrieve(
            @PathVariable Long documentId,
            @RequestParam String question ) {
        return retrievalService.retrieve(question,documentId);
    }

//    For asking question from the model and genrating the answer
    @PostMapping("/qa/{documentId}")
    public String askQuestion(
            @PathVariable Long documentId,
            @Valid @RequestBody QuestionRequest request ) {
        return qaService.genrateAnswer(request.question(),documentId);
    }


    @DeleteMapping("/{documentId}")
    public ResponseEntity<String> deleteDocument(@PathVariable Long documentId){
        documentEntityService.deleteDocument(documentId);
        return ResponseEntity.ok(
                "Document and its embeddings deleted successfully"
        );
    }



}
