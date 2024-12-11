package com.wzn.expensetrackerv2.service.botService;

import com.wzn.expensetrackerv2.dto.CompletionTokensDetails;
import com.wzn.expensetrackerv2.dto.PromptTokensDetails;

import java.util.Map;

public interface UsageService {

    // Getters
    Integer getPromptTokens();
    Integer getCompletionTokens();
    Integer getTotalTokens();
    Map<String, Object> getAdditionalProperties();
    PromptTokensDetails getPromptTokensDetails();
    CompletionTokensDetails getCompletionTokensDetails();

    // Setters
    boolean setPromptTokens(Integer promptTokens);
    boolean setCompletionTokens(Integer completionTokens);
    boolean setTotalTokens(Integer totalTokens);
    boolean setAdditionalProperty(String key, Object value);
    boolean setPromptTokensDetails(PromptTokensDetails promptTokensDetails);
    boolean setCompletionTokensDetails(CompletionTokensDetails completionTokensDetails);
}
