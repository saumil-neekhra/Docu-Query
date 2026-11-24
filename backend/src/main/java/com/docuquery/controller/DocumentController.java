package com.docuquery.controller;

import com.docuquery.rag.generation.DocumentQAService;
import com.docuquery.rag.ingestion.DocumentIngestionService;
import com.docuquery.rag.retrieval.DocumentRetrievalService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentIngestionService ingestionService;
    private final DocumentRetrievalService retrievalService;
    private final DocumentQAService qaService;

//    For Checking Connection
    @GetMapping("")
    public ResponseEntity<String> checking(){
        return ResponseEntity.ok("ok");
    }

//    For uploading document and creating vector embeddings in vectorStore DB.
    @PostMapping("/upload")
    public ResponseEntity<String> uploadDocument(@RequestParam("file")MultipartFile file) throws IOException{

        Long documentId = 1L;

        ingestionService.ingestDocument(file,documentId);

        return ResponseEntity.ok("Document uploaded and indexed successfully");

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
            @RequestBody QuestionRequest request ) {
        return qaService.genrateAnswer(request.question(),documentId);
    }

    public record QuestionRequest(String question) {}

}
