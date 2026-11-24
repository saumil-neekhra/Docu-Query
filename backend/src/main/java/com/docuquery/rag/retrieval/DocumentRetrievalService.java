package com.docuquery.rag.retrieval;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentRetrievalService {

    private final VectorStore vectorStore;

    public List<Document> retrieve(String question, Long documentId) {

        SearchRequest request = SearchRequest.builder()
                .query(question)
                .topK(5)
                .filterExpression("documentId == " + documentId)
                .build();

        return vectorStore.similaritySearch(request);
    }
}
