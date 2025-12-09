package controller;


import com.spring.ai.demo.controller.ChatController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class ChatControllerTest {


    private ChatClient chatClient;
    private ChatController chatController;
    private ChatClient.ChatClientRequestSpec requestSpec;
    private ChatClient.CallResponseSpec callResponseSpec;

    @BeforeEach
    void setUp() {
        // Create a mock ChatClient
        chatClient = mock(ChatClient.class);
        requestSpec = mock(ChatClient.ChatClientRequestSpec.class);
        callResponseSpec = mock(ChatClient.CallResponseSpec.class);

        // Initialize your service with the mocked ChatClient
        chatController = new ChatController(chatClient);
    }

    @Test
    public void chatTest() {

            String expectedContent = "Hello from AI!";

            // 1. chatClient.prompt("...") → returns requestSpec
            when(chatClient.prompt(anyString())).thenReturn(requestSpec);

            // 2. requestSpec.call() → returns callResponseSpec
            when(requestSpec.call()).thenReturn(callResponseSpec);

            // 3. callResponseSpec.content() → returns the expected content
            when(callResponseSpec.content()).thenReturn(expectedContent);

            // Call controller
            ResponseEntity<String> response = chatController.chat("User query");

            // Assert result
            assertEquals(expectedContent, response.getBody());


    }
}
