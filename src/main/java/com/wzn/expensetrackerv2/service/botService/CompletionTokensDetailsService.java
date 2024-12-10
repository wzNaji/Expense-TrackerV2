package com.wzn.expensetrackerv2.service.botService;

import java.util.Map;

public interface CompletionTokensDetailsService {

    // Abstract methods for getters
    Integer getReasoningTokens();
    Integer getAudioTokens();
    Integer getAcceptedPredictionTokens();
    Integer getRejectedPredictionTokens();
    Map<String, Object> getAdditionalProperties();

    // Abstract methods for setters (returning boolean)
    boolean setReasoningTokens(Integer reasoningTokens);
    boolean setAudioTokens(Integer audioTokens);
    boolean setAcceptedPredictionTokens(Integer acceptedPredictionTokens);
    boolean setRejectedPredictionTokens(Integer rejectedPredictionTokens);
    boolean setAdditionalProperty(String key, Object value);
}
