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

### 1. Start Vector Database
Start the PostgreSQL container with `pgvector` enabled:
```bash
docker compose up -d
```

### 2. Run the Backend
```bash
cd backend
./mvnw spring-boot:run
```

### 3. Setup Frontend
Add your frontend application files inside the `frontend/` directory.
