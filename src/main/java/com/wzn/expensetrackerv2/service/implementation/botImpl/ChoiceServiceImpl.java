package com.wzn.expensetrackerv2.service.implementation.botImpl;

import com.wzn.expensetrackerv2.dto.Choice;
import com.wzn.expensetrackerv2.dto.Message;
import com.wzn.expensetrackerv2.service.botService.ChoiceService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ChoiceServiceImpl implements ChoiceService {

    private final Choice choice;

    public ChoiceServiceImpl(Choice choice) {
        this.choice = choice;
    }

    @Override
    // Getters
    public Integer getIndex() {
        return choice.getIndex();
    }

    @Override
    public Message getMessage() {
        return choice.getMessage();
    }

    @Override
    public Object getLogprobs() {
        return choice.getLogprobs();
    }

    @Override
    public String getFinishReason() {
        return choice.getFinishReason();
    }

    @Override
    public Map<String, Object> getAdditionalProperties() {
        return choice.getAdditionalProperties();
    }

    @Override
    // Setters
    public boolean setIndex(Integer index) {
        choice.setIndex(index);
        return choice.getIndex() != null && choice.getIndex().equals(index);
    }

    @Override
    public boolean setMessage(Message message) {
        choice.setMessage(message);
        return choice.getMessage() != null && choice.getMessage().equals(message);
    }

    @Override
    public boolean setLogprobs(Object logprobs) {
        choice.setLogprobs(logprobs);
        return choice.getLogprobs() != null && choice.getLogprobs().equals(logprobs);
    }

    @Override
    public boolean setFinishReason(String finishReason) {
        choice.setFinishReason(finishReason);
        return choice.getFinishReason() != null && choice.getFinishReason().equals(finishReason);
    }

    @Override
    public boolean setAdditionalProperty(String key, Object value) {
        choice.getAdditionalProperties().put(key, value);
        return choice.getAdditionalProperties().get(key) != null && choice.getAdditionalProperties().get(key).equals(value);
    }
}
