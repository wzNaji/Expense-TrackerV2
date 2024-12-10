package com.wzn.expensetrackerv2.service.implementation.botImpl;

import com.wzn.expensetrackerv2.dto.ChatRequest;
import com.wzn.expensetrackerv2.dto.Message;
import com.wzn.expensetrackerv2.service.botService.ChatRequestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatRequestServiceImpl implements ChatRequestService {

    private final ChatRequest chatRequest;

    public ChatRequestServiceImpl(ChatRequest chatRequest) {
        this.chatRequest = chatRequest;
    }

    @Override
    public Boolean setModel(String model) {
        chatRequest.setModel(model);
        return chatRequest.getModel() != null;
    }

    @Override
    public String getModel() {
        return chatRequest.getModel();
    }

    @Override
    public Boolean setMessages(List<Message> messages) {
        chatRequest.setMessages(messages);
        return chatRequest.getMessages() != null && !chatRequest.getMessages().isEmpty();
    }

    @Override
    public List<Message> getMessages() {
        return chatRequest.getMessages();
    }

    @Override
    public Boolean setN(Integer n) {
        chatRequest.setN(n);
        return chatRequest.getN() != null;
    }

    @Override
    public Integer getN() {
        return chatRequest.getN();
    }

    @Override
    public Boolean setTemperature(Integer temperature) {
        chatRequest.setTemperature(temperature);
        return chatRequest.getTemperature() != null;
    }

    @Override
    public Integer getTemperature() {
        return chatRequest.getTemperature();
    }

    @Override
    public Boolean setMaxTokens(Integer maxTokens) {
        chatRequest.setMaxTokens(maxTokens);
        return chatRequest.getMaxTokens() != null;
    }

    @Override
    public Integer getMaxTokens() {
        return chatRequest.getMaxTokens();
    }

    @Override
    public Boolean setStream(Boolean stream) {
        chatRequest.setStream(stream);
        return chatRequest.getStream() != null && chatRequest.getStream().equals(stream);
    }

    public Boolean getStream() {
        return chatRequest.getStream();
    }

    @Override
    public Boolean setPresencePenalty(Integer presencePenalty) {
        chatRequest.setPresencePenalty(presencePenalty);
        return chatRequest.getPresencePenalty() != null;
    }

    @Override
    public Integer getPresencePenalty() {
        return chatRequest.getPresencePenalty();
    }
}
