package com.wzn.expensetrackerv2.service.botService;

import com.wzn.expensetrackerv2.dto.Message;

import java.util.List;

public interface ChatRequestService {

    // Setters
    Boolean setModel(String model);
    Boolean setMessages(List<Message> messages);
    Boolean setN(Integer n);
    Boolean setTemperature(Integer temperature);
    Boolean setMaxTokens(Integer maxTokens);
    Boolean setStream(Boolean stream);
    Boolean setPresencePenalty(Integer presencePenalty);

    // Getters
    String getModel();
    List<Message> getMessages();
    Integer getN();
    Integer getTemperature();
    Integer getMaxTokens();
    Boolean getStream();
    Integer getPresencePenalty();
}
