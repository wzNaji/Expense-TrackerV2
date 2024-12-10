package com.wzn.expensetrackerv2.service.botService;

import com.wzn.expensetrackerv2.dto.Message;

import java.util.Map;

public interface ChoiceService {

    // Abstract methods for getters
    Integer getIndex();
    Message getMessage();
    Object getLogprobs();
    String getFinishReason();
    Map<String, Object> getAdditionalProperties();

    // Abstract methods for setters (returning boolean)
    boolean setIndex(Integer index);
    boolean setMessage(Message message);
    boolean setLogprobs(Object logprobs);
    boolean setFinishReason(String finishReason);
    boolean setAdditionalProperty(String key, Object value);
}
