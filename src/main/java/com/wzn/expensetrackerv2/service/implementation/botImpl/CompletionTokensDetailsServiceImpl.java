package com.wzn.expensetrackerv2.service.implementation.botImpl;

import com.wzn.expensetrackerv2.dto.CompletionTokensDetails;
import com.wzn.expensetrackerv2.service.botService.CompletionTokensDetailsService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CompletionTokensDetailsServiceImpl implements CompletionTokensDetailsService {

    private final CompletionTokensDetails completionTokensDetails;

    public CompletionTokensDetailsServiceImpl(CompletionTokensDetails completionTokensDetails) {
        this.completionTokensDetails = completionTokensDetails;
    }

    @Override
    // Getters
    public Integer getReasoningTokens() {
        return completionTokensDetails.getReasoningTokens();
    }

    @Override
    public Integer getAudioTokens() {
        return completionTokensDetails.getAudioTokens();
    }

    @Override
    public Integer getAcceptedPredictionTokens() {
        return completionTokensDetails.getAcceptedPredictionTokens();
    }

    @Override
    public Integer getRejectedPredictionTokens() {
        return completionTokensDetails.getRejectedPredictionTokens();
    }

    @Override
    public Map<String, Object> getAdditionalProperties() {
        return completionTokensDetails.getAdditionalProperties();
    }

    @Override
    // Setters
    public boolean setReasoningTokens(Integer reasoningTokens) {
        completionTokensDetails.setReasoningTokens(reasoningTokens);
        return completionTokensDetails.getReasoningTokens() != null && completionTokensDetails.getReasoningTokens().equals(reasoningTokens);
    }

    @Override
    public boolean setAudioTokens(Integer audioTokens) {
        completionTokensDetails.setAudioTokens(audioTokens);
        return completionTokensDetails.getAudioTokens() != null && completionTokensDetails.getAudioTokens().equals(audioTokens);
    }

    @Override
    public boolean setAcceptedPredictionTokens(Integer acceptedPredictionTokens) {
        completionTokensDetails.setAcceptedPredictionTokens(acceptedPredictionTokens);
        return completionTokensDetails.getAcceptedPredictionTokens() != null && completionTokensDetails.getAcceptedPredictionTokens().equals(acceptedPredictionTokens);
    }

    @Override
    public boolean setRejectedPredictionTokens(Integer rejectedPredictionTokens) {
        completionTokensDetails.setRejectedPredictionTokens(rejectedPredictionTokens);
        return completionTokensDetails.getRejectedPredictionTokens() != null && completionTokensDetails.getRejectedPredictionTokens().equals(rejectedPredictionTokens);
    }

    @Override
    public boolean setAdditionalProperty(String key, Object value) {
        completionTokensDetails.getAdditionalProperties().put(key, value);
        return completionTokensDetails.getAdditionalProperties().get(key) != null && completionTokensDetails.getAdditionalProperties().get(key).equals(value);
    }
}
