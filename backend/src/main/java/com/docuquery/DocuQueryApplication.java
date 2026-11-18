package com.docuquery;

import org.springframework.ai.vectorstore.pgvector.autoconfigure.PgVectorStoreAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
		exclude = {
				PgVectorStoreAutoConfiguration.class
		}
)
public class DocuQueryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocuQueryApplication.class, args);
	}

}
