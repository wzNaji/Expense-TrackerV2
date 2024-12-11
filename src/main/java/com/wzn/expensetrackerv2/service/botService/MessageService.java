package com.wzn.expensetrackerv2.service.botService;

import java.util.Map;

public interface MessageService {

    // Abstract methods for getters
    String getRole();
    String getContent();
    Object getRefusal();
    Map<String, Object> getAdditionalProperties();

    // Abstract methods for setters (returning boolean)
    boolean setRole(String role);
    boolean setContent(String content);
    boolean setRefusal(Object refusal);
    boolean setAdditionalProperty(String key, Object value);
}
