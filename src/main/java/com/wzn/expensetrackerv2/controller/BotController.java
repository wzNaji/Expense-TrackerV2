package com.wzn.expensetrackerv2.controller;

import com.wzn.expensetrackerv2.dto.ChatRequest;
import com.wzn.expensetrackerv2.dto.ChatResponse;
import com.wzn.expensetrackerv2.dto.Message;
import com.wzn.expensetrackerv2.entity.Expense;
import com.wzn.expensetrackerv2.entity.Month;
import com.wzn.expensetrackerv2.service.MonthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@RestController
@RequestMapping("/api")
public class BotController {

    private final WebClient webClient;
    private final MonthService monthService;

    public BotController(WebClient webClient, MonthService monthService) {
        this.webClient = webClient;
        this.monthService = monthService;
    }


    @GetMapping("/bot")
    public ResponseEntity<Map<String, Object>> chatbot(@RequestParam String message, Authentication authentication) {
        Map<String, Object> response = new HashMap<>();

        // Step 1: Check Authentication
        ResponseEntity<Map<String, Object>> authCheck = checkAuthentication(authentication);
        if (authCheck != null) {
            return authCheck;
        }

        // Step 2: Check Token Limit
        ResponseEntity<Map<String, Object>> tokenCheck = checkTokenLimit(message);
        if (tokenCheck != null) {
            return tokenCheck;
        }

        // Step 3: Parse and Validate Message Format
        List<String> separatedMessages = parseAndValidateMessage(message, response);
        if (separatedMessages == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Step 4: Parse Year and Month
        Integer year = null;
        Integer month = null;
        try {
            year = Integer.parseInt(separatedMessages.get(0));
            month = Integer.parseInt(separatedMessages.get(1));
        } catch (NumberFormatException e) {
            response.put("success", false);
            response.put("message", "Look at the example and try again.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Step 5: Find Month and Expenses
        Month foundMonth = findMonth(year, month, response);
        if (foundMonth == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // Step 6: Find Expense
        Expense foundExpense = findExpense(foundMonth.getListOfExpenses(), separatedMessages.get(2), response);
        if (foundExpense == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // Step 7: Prepare ChatRequest
        ChatRequest chatRequest = buildChatRequest(foundExpense, separatedMessages.get(3));

        // Step 8: Call the GPT API
        ChatResponse chatResponse = callGptApi(chatRequest, response);
        if (chatResponse == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        // Step 9: Process GPT Response
        ResponseEntity<Map<String, Object>> finalResponse = processGptResponse(chatResponse, response);
        return finalResponse;
    }


    private ResponseEntity<Map<String, Object>> checkAuthentication(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            Map<String, Object> res = new HashMap<>();
            res.put("success", false);
            res.put("message", "Unauthorized access");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(res);
        }
        return null; // Means authentication passed
    }

    private ResponseEntity<Map<String, Object>> checkTokenLimit(String message) {
        if (!isUnder50Tokens(message)) {
            Map<String, Object> res = new HashMap<>();
            res.put("success", false);
            res.put("message", "You are not under 50-tokens");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(res);
        }
        return null; // Means token check passed
    }

    private List<String> parseAndValidateMessage(String message, Map<String, Object> response) {
        List<String> separatedMessages = Arrays.asList(message.split(", "));
        if (separatedMessages.size() < 4) {
            response.put("success", false);
            response.put("message", "Invalid input format. Expected format: year, month, expense_name, user_query");
            return null;
        }
        return separatedMessages;
    }

    private Month findMonth(int year, int month, Map<String, Object> response) {
        Month foundMonth = monthService.findByYearAndMonth(year, month);
        if (foundMonth == null) {
            response.put("success", false);
            response.put("message", "No data found for the given year and month.");
            return null;
        }
        return foundMonth;
    }

    private Expense findExpense(List<Expense> expenses, String expenseName, Map<String, Object> response) {
        for (Expense expense : expenses) {
            if (expense.getItemName().equalsIgnoreCase(expenseName)) {
                return expense;
            }
        }
        response.put("success", false);
        response.put("message", "Expense not found in the specified month.");
        return null;
    }

    private ChatRequest buildChatRequest(Expense foundExpense, String userQuery) {
        ChatRequest chatRequest = new ChatRequest();
        List<Message> messagesList = new ArrayList<>();

        messagesList.add(new Message("system", "You are a personal financial advisor for Parmar. Parmar is the owner of a convenience store called 'Shop n Play', in Copenhagen. Give short and precise answers. Finish the response in under 500 chars."));
        String expenseDetails = String.format("Name: %s, Price: %.2f kr, Description: %s",
                foundExpense.getItemName(),
                foundExpense.getPrice(),
                foundExpense.getDescription());
        messagesList.add(new Message("user", "Here are the details of the expense: " + expenseDetails));
        messagesList.add(new Message("user", userQuery));

        chatRequest.setMessages(messagesList);
        chatRequest.setModel("gpt-4o");
        chatRequest.setN(1);
        chatRequest.setTemperature(0);
        chatRequest.setMaxTokens(200);
        chatRequest.setStream(false);
        chatRequest.setPresencePenalty(0);

        return chatRequest;
    }

    private ChatResponse callGptApi(ChatRequest chatRequest, Map<String, Object> response) {
        ChatResponse chatResponse;
        try {
            chatResponse = webClient.post()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(chatRequest)
                    .retrieve()
                    .bodyToMono(ChatResponse.class)
                    .block();
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Webclient error occurred.");
            return null;
        }

        if (chatResponse == null || chatResponse.getChoices() == null || chatResponse.getChoices().isEmpty()) {
            response.put("success", false);
            response.put("message", "No response received from the AI service.");
            // Returning null here will be handled in the main method
            return null;
        }

        return chatResponse;
    }

    private ResponseEntity<Map<String, Object>> processGptResponse(ChatResponse chatResponse, Map<String, Object> response) {
        String botResponse = chatResponse.getChoices().get(0).getMessage().getContent();
        Integer promptTokens = chatResponse.getUsage().getPromptTokens();
        Integer totalTokens = chatResponse.getUsage().getTotalTokens();

        response.put("success", true);
        response.put("message", botResponse);
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
