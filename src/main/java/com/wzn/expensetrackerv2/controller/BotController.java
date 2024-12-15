package com.wzn.expensetrackerv2.controller;

import com.wzn.expensetrackerv2.dto.ChatRequest;
import com.wzn.expensetrackerv2.dto.ChatResponse;
import com.wzn.expensetrackerv2.dto.Message;
import com.wzn.expensetrackerv2.entity.Expense;
import com.wzn.expensetrackerv2.entity.Month;
import com.wzn.expensetrackerv2.service.ExpenseService;
import com.wzn.expensetrackerv2.service.MonthService;
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

        // Authentication check
        if (authentication == null || !authentication.isAuthenticated()) {
            response.put("success", false);
            response.put("message", "Unauthorized access");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        // Token limit check
        if (!isUnder50Tokens(message)) {
            response.put("success", false);
            response.put("message", "You are not under 50-tokens");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        // Split the user message into parts
        List<String> separatedMessages = Arrays.asList(message.split(", "));
        if (separatedMessages.size() < 4) {
            response.put("success", false);
            response.put("message", "Invalid input format. Expected format: year, month, expense_name, user_query");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Parse year and month
        int year;
        int month;
        try {
            year = Integer.parseInt(separatedMessages.get(0));
            month = Integer.parseInt(separatedMessages.get(1));
        } catch (NumberFormatException e) {
            response.put("success", false);
            response.put("message", "Look at the example and try again.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Fetch the month and expenses from the database
        Month foundMonth = monthService.findByYearAndMonth(year, month);
        if (foundMonth == null) {
            response.put("success", false);
            response.put("message", "No data found for the given year and month.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        List<Expense> listOfExpensesOfMonth = foundMonth.getListOfExpenses();
        Expense foundExpense = null;

        // Match the expense name
        String expenseName = separatedMessages.get(2);
        for (Expense expense : listOfExpensesOfMonth) {
            if (expense.getItemName().equalsIgnoreCase(expenseName)) {
                foundExpense = expense;
                break;
            }
        }

        if (foundExpense == null) {
            response.put("success", false);
            response.put("message", "Expense not found in the specified month.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // Prepare ChatRequest
        ChatRequest chatRequest = new ChatRequest();
        List<Message> messagesList = new ArrayList<>();

        // System role: setting the assistant's persona
        messagesList.add(new Message("system", "You are a personal financial advisor for Parmar. Parmar is the owner of a convenience store called 'Shop n Play', in Copenhagen. Give short and precise answers. Finish the response in under 500 chars."));
        String expenseDetails = String.format(
                "Name: %s, Price: %.2f kr, Description: %s",
                foundExpense.getItemName(),
                foundExpense.getPrice(),
                foundExpense.getDescription()
        );
        messagesList.add(new Message("user", "Here are the details of the expense: " + expenseDetails));


        // User role: adding the user's specific query
        String userQuery = separatedMessages.get(3);
        messagesList.add(new Message("user", userQuery));

        chatRequest.setMessages(messagesList);
        chatRequest.setModel("gpt-4o");
        chatRequest.setN(1);
        chatRequest.setTemperature(0);
        chatRequest.setMaxTokens(200);
        chatRequest.setStream(false);
        chatRequest.setPresencePenalty(0);

        // Call the GPT API
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
            response.put("message", "Webclient error occurred.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        if (chatResponse == null || chatResponse.getChoices() == null || chatResponse.getChoices().isEmpty()) {
            response.put("success", false);
            response.put("message", "No response received from the AI service.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }

        // Extract response details
        String botResponse = chatResponse.getChoices().get(0).getMessage().getContent();
        Integer promptTokens = chatResponse.getUsage().getPromptTokens();
        Integer totalTokens = chatResponse.getUsage().getTotalTokens();

        // Prepare final response
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
