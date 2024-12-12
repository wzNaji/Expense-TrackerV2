package com.wzn.expensetrackerv2.controller;

import com.wzn.expensetrackerv2.dto.ChatRequest;
import com.wzn.expensetrackerv2.dto.ChatResponse;
import com.wzn.expensetrackerv2.dto.Message;
import com.wzn.expensetrackerv2.service.botService.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BotController {

    private final WebClient webClient;

    public BotController(WebClient webClient, ChatRequestService chatRequestService) {
        this.webClient = webClient;
    }


    @GetMapping("/bot")
    public ResponseEntity<Map<String, Object>> chatbot(@RequestParam String message, Authentication authentication) {
        Map<String, Object> response = new HashMap<>();

        if (authentication == null || !authentication.isAuthenticated()) {
            response.put("success", false);
            response.put("message", "Unauthorized access");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        if (!isUnder50Tokens(message)) {
            response.put("success", false);
            response.put("message", "You are not under 50-tokens");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        ChatRequest chatRequest = new ChatRequest();
        List<Message> messagesList = new ArrayList<>();

        messagesList.add(new Message("system", "Du er en personlig finansiel rådgiveer for Parmar. Parma Er ejer af en døgn-kiosk ved navn 'Shop n Play'."));
        messagesList.add(new Message("user", message));

        chatRequest.setMessages(messagesList);
        chatRequest.setModel("gpt-4o");
        chatRequest.setN(1);
        chatRequest.setTemperature(0);
        chatRequest.setMaxTokens(200);
        chatRequest.setStream(false);
        chatRequest.setPresencePenalty(0);

        ChatResponse chatResponse = null;

        try {
            chatResponse = webClient.post()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(chatRequest)
                    .retrieve()
                    .bodyToMono(ChatResponse.class)
                    .block();
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Webclient error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        if (chatResponse == null || chatResponse.getChoices() == null || chatResponse.getChoices().isEmpty()) {
            response.put("success", false);
            response.put("message", "No response received from OpenAI");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }

        Integer promptTokens = chatResponse.getUsage().getPromptTokens();
        Integer totalTokens = chatResponse.getUsage().getTotalTokens();

        response.put("success", true);
        response.put("message", chatResponse.getChoices().get(0).getMessage().getContent());
        response.put("promptTokens", promptTokens);
        response.put("totalTokens", totalTokens);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    private boolean isUnder50Tokens(String message) {
        message = message.trim();
        int charCount = message.length();
        double estimatedTokens = charCount / 4.0; // Estimate tokens as 1 token per 4 characters
        return estimatedTokens < 50;
    }

}
