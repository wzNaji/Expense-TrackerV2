package com.wzn.expensetrackerv2.service.implementation.botImpl;

import com.wzn.expensetrackerv2.dto.Message;
import com.wzn.expensetrackerv2.service.botService.MessageService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {

    private final Message message;

    public MessageServiceImpl(Message message) {
        this.message = message;
    }

    @Override
    // Getters
    public String getRole() {
        return message.getRole();
    }

    @Override
    public String getContent() {
        return message.getContent();
    }

    @Override
    public Object getRefusal() {
        return message.getRefusal();
    }

    @Override
    public Map<String, Object> getAdditionalProperties() {
        return message.getAdditionalProperties();
    }

    @Override
    // Setters
    public boolean setRole(String role) {
        message.setRole(role);
        return message.getRole() != null && message.getRole().equals(role);
    }

    @Override
    public boolean setContent(String content) {
        message.setContent(content);
        return message.getContent() != null && message.getContent().equals(content);
    }

    @Override
    public boolean setRefusal(Object refusal) {
        message.setRefusal(refusal);
        return message.getRefusal() != null && message.getRefusal().equals(refusal);
    }

    @Override
    public boolean setAdditionalProperty(String key, Object value) {
        message.getAdditionalProperties().put(key, value);
        return message.getAdditionalProperties().get(key) != null && message.getAdditionalProperties().get(key).equals(value);
    }
}
