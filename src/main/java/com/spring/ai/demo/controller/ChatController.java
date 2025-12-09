package com.spring.ai.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(@Qualifier("docSearchClientBean") ChatClient chatClient){
        this.chatClient = chatClient;
    }
    @Operation(
            summary = "Ask a question to the AI",
            description = "Uses RAG, Qdrant and Spring AI to answer organization-specific questions"
    )
    @GetMapping("/chat")
    public ResponseEntity<String> chat(@RequestParam ("message") String message){
        String response = chatClient.prompt(message).call().content();
        return ResponseEntity.ok(response);
    }

}
