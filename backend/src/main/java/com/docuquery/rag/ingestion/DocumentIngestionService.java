package com.docuquery.rag.ingestion;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentIngestionService {

    private final VectorStore vectorStore;

    public void ingestDocument(MultipartFile file, Long documentId) throws IOException {

//        1. Read PDF
        PagePdfDocumentReader reader = new PagePdfDocumentReader(
                new InputStreamResource(file.getInputStream())
        );

        List<Document> documents = reader.get();

//        2. Split into chunks
        TokenTextSplitter splitter =  TokenTextSplitter.builder()
                .withChunkSize(800)
                .withMinChunkLengthToEmbed(250)
                .withMinChunkSizeChars(5)
                .withMaxNumChunks(10000)
                .withKeepSeparator(true)
                .build();

        List<Document> chunks = splitter.apply(documents);

//        3. Add metadata
        for(Document chunk : chunks){
            chunk.getMetadata().put("documentId",documentId);
            chunk.getMetadata().put("filename",file.getOriginalFilename());
        }

        vectorStore.add(chunks);
    }
}
