package com.wzn.expensetrackerv2.service.botService;

import java.util.Map;

public interface PromptTokensDetailsService {

    // Abstract methods for getters
    Integer getCachedTokens();
    Integer getAudioTokens();
    Map<String, Object> getAdditionalProperties();

    // Abstract methods for setters
    boolean setCachedTokens(Integer cachedTokens);
    boolean setAudioTokens(Integer audioTokens);
    boolean setAdditionalProperty(String key, Object value);
}
