# DocuQuery

An intelligent Document Retrieval and Context-Augmented Generation (RAG) platform powered by Spring Boot, Spring AI, PostgreSQL with pgvector, and a modern frontend client.

## Project Structure

```text
docuquery/
│
├── backend/
│   ├── src/main/java/
│   │   └── com/docuquery/
│   │       ├── config/           # App, Spring AI, Security, OpenAPI, and CORS configurations
│   │       ├── controller/       # REST API endpoints (document upload, query, retrieval)
│   │       ├── service/          # Business logic and coordination services
│   │       ├── repository/       # Spring Data JPA repositories
│   │       ├── entity/           # Database entity models
│   │       ├── dto/              # Data transfer objects (Requests / Responses)
│   │       ├── security/         # Authentication, authorization, and filters
│   │       ├── exception/        # Custom exceptions and global exception handlers
│   │       └── rag/
│   │           ├── ingestion/    # Document parsing, chunking, and embedding pipelines
│   │           ├── retrieval/    # Vector similarity search and context ranking
│   │           └── generation/   # LLM prompt templates and response synthesis
│   ├── src/main/resources/       # application.properties and static resources
│   └── pom.xml                   # Maven dependencies (Spring Boot, Spring AI, pgvector)
│
├── frontend/                     # Frontend client application (React, Next.js, etc.)
│
├── docker-compose.yml            # Local development infrastructure (PostgreSQL with pgvector)
└── README.md
```

## Getting Started

### Prerequisites
- **Java 21** or later
- **Docker & Docker Compose** (for vector database)
- **Node.js** (for frontend development)

## Initial Backend Setup

### Initialized the Java 21 and Spring Boot backend.

Configured PostgreSQL 18 connectivity using Spring Data JPA and Hibernate.

Installed and enabled the pgvector PostgreSQL extension.

Created a dedicated database user with appropriate privileges.

Configured database credentials through environment variables instead of hardcoding secrets.

Verified successful database connectivity and Hibernate initialization.

### Current Status:

Basic Spring Boot application and PostgreSQL connection are working.

No document ingestion, retrieval, or RAG functionality has been implemented yet.

Spring AI model auto-configuration (chat and embeddings) is temporarily disabled.

PGVector auto-configuration is temporarily excluded until the embedding model is configured.

#### Next Step: Configure the AI model and embedding integration, then implement document ingestion and retrieval.
