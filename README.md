This project is a Spring Boot application that enables users to ask organization-specific questions.
Using RAG (Retrieval Augmented Generation) with Spring AI, OpenAI, and Qdrant Vector Store, the system retrieves relevant information from company documents and generates concise answers using an LLM.

🚀 Features
✅ Retrieval Augmented Generation (RAG)
User queries are embedded using OpenAI.
Relevant text chunks are retrieved from a Qdrant vector database.
The LLM generates an answer grounded only in retrieved company documents.

✅ Qdrant Vector Database Integration
Uses the Spring AI VectorStore abstraction with Qdrant as the backend.
Automatically loads and embeds documents stored inside src/main/resources.

✅ Document Ingestion
PDF documents (e.g., contracts, policies, case summaries, memos) are stored in
src/main/resources/
On application startup:
Qdrant is checked for existing data.
If the collection is empty, documents are chunked and embedded.
Duplicate ingestion is avoided.

✅ Spring AI ChatClient
Routes prompts to OpenAI along with retrieved context from Qdrant.
AI responses are concise and context-aware.

✅ Environment-Based API Key Management
No secrets in code.
OpenAI API key is loaded from environment variables:
export OPENAI_API_KEY=your_api_key_here

✅ JUnit Test Support
The project includes a mocked JUnit test for the ChatController in which:
ChatClient.prompt().call().content() is mocked.
No real OpenAI or Qdrant service calls are performed.
Verifies that the controller returns expected AI responses.


📦 Tech Stack:
Spring Boot 3
Spring AI
OpenAI API
Qdrant Vector Database
Apache Tika
JUnit 5 + Mockito
Maven
