package com.docuquery.service;

import com.docuquery.entity.DocumentEntity;
import com.docuquery.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class DocumentEntityService {

    private final DocumentRepository documentRepository;
    private final VectorStore vectorStore;

    @Transactional
    public void deleteDocument(Long documentId){
        DocumentEntity document = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Document not found"
                ));

        // Delete all chunks belonging to this document.
        // Spring AI supports deleting vector records using metadata filter expressions.
        vectorStore.delete("documentId == " + documentId);

        // Delete document metadata.
        documentRepository.delete(document);
    }
}
