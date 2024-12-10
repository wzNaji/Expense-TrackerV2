package com.wzn.expensetrackerv2.service.implementation.botImpl;

import com.wzn.expensetrackerv2.dto.CompletionTokensDetails;
import com.wzn.expensetrackerv2.dto.PromptTokensDetails;
import com.wzn.expensetrackerv2.dto.Usage;
import com.wzn.expensetrackerv2.service.botService.UsageService;

import java.util.Map;

public class UsageServiceImpl implements UsageService {

    private final Usage usage;

    public UsageServiceImpl(Usage usage) {
        this.usage = usage;
    }

    @Override
    public Integer getPromptTokens() {
        return usage.getPromptTokens();
    }

    @Override
    public Integer getCompletionTokens() {
        return usage.getCompletionTokens();
    }

    @Override
    public Integer getTotalTokens() {
        return usage.getTotalTokens();
    }

    @Override
    public Map<String, Object> getAdditionalProperties() {
        return usage.getAdditionalProperties();
    }

    @Override
    public boolean setPromptTokens(Integer promptTokens) {
        usage.setPromptTokens(promptTokens);
        return usage.getPromptTokens() != null && usage.getPromptTokens().equals(promptTokens);
    }

    @Override
    public boolean setCompletionTokens(Integer completionTokens) {
        usage.setCompletionTokens(completionTokens);
        return usage.getCompletionTokens() != null && usage.getCompletionTokens().equals(completionTokens);
    }

    @Override
    public boolean setTotalTokens(Integer totalTokens) {
        usage.setTotalTokens(totalTokens);
        return usage.getTotalTokens() != null && usage.getTotalTokens().equals(totalTokens);
    }

    @Override
    public boolean setAdditionalProperty(String key, Object value) {
        usage.getAdditionalProperties().put(key, value);
        return usage.getAdditionalProperties().get(key) != null && usage.getAdditionalProperties().get(key).equals(value);
    }

    @Override
    public boolean setPromptTokensDetails(PromptTokensDetails promptTokensDetails) {
        usage.setPromptTokensDetails(promptTokensDetails);
        return usage.getPromptTokensDetails() != null && usage.getPromptTokensDetails().equals(promptTokensDetails);
    }

    @Override
    public boolean setCompletionTokensDetails(CompletionTokensDetails completionTokensDetails) {
        usage.setCompletionTokensDetails(completionTokensDetails);
        return usage.getCompletionTokensDetails() != null && usage.getCompletionTokensDetails().equals(completionTokensDetails);
    }

    @Override
    public PromptTokensDetails getPromptTokensDetails() {
        return usage.getPromptTokensDetails();
    }

    @Override
    public CompletionTokensDetails getCompletionTokensDetails() {
        return usage.getCompletionTokensDetails();
    }
}
