package com.spring.ai.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringAIApplication {

	static void main(String[] args) {
		SpringApplication.run(SpringAIApplication.class, args);
	}

}

/*
Create a Spring Boot application that allows users to upload legal documents (e.g., contracts, case summaries).
Implement a RAG system where user queries are used to retrieve relevant sections from these documents,
and then an LLM provides concise answers based on the retrieved context.
This highlights expertise in domain-specific AI, information retrieval, and RAG.
*/

/*
    - read llm
    - store in vector db
    - take user input - embed user/system advisor texts
    - take data from vector store and return it to llm
    - response back to user
*/

