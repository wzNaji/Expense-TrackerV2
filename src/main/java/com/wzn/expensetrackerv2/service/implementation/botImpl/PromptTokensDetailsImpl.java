package com.wzn.expensetrackerv2.service.implementation.botImpl;

import com.wzn.expensetrackerv2.dto.PromptTokensDetails;
import com.wzn.expensetrackerv2.service.botService.PromptTokensDetailsService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PromptTokensDetailsImpl implements PromptTokensDetailsService {

    private final PromptTokensDetails promptTokensDetails;

    public PromptTokensDetailsImpl(PromptTokensDetails promptTokensDetails) {
        this.promptTokensDetails = promptTokensDetails;
    }

    @Override
    public Integer getCachedTokens() {
        return promptTokensDetails.getCachedTokens();
    }

    @Override
    public Integer getAudioTokens() {
        return promptTokensDetails.getAudioTokens();
    }

    @Override
    public Map<String, Object> getAdditionalProperties() {
        return promptTokensDetails.getAdditionalProperties();
    }

    @Override
    public boolean setCachedTokens(Integer cachedTokens) {
        promptTokensDetails.setCachedTokens(cachedTokens);
        return promptTokensDetails.getCachedTokens() != null && promptTokensDetails.getCachedTokens().equals(cachedTokens);
    }

    @Override
    public boolean setAudioTokens(Integer audioTokens) {
        promptTokensDetails.setAudioTokens(audioTokens);
        return promptTokensDetails.getAudioTokens() != null && promptTokensDetails.getAudioTokens().equals(audioTokens);
    }

    @Override
    public boolean setAdditionalProperty(String key, Object value) {
        promptTokensDetails.getAdditionalProperties().put(key, value);
        return promptTokensDetails.getAdditionalProperties().get(key) != null && promptTokensDetails.getAdditionalProperties().get(key).equals(value);
    }
}
