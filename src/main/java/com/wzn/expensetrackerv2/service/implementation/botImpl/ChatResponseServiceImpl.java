package com.wzn.expensetrackerv2.service.implementation.botImpl;

import com.wzn.expensetrackerv2.dto.ChatResponse;
import com.wzn.expensetrackerv2.dto.Choice;
import com.wzn.expensetrackerv2.dto.Usage;
import com.wzn.expensetrackerv2.service.botService.ChatResponseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatResponseServiceImpl implements ChatResponseService {

    private final ChatResponse chatResponse;

    public ChatResponseServiceImpl(ChatResponse chatResponse) {
        this.chatResponse = chatResponse;
    }

    @Override
    // Optimized Setter for id
    public boolean setId(String id) {
        chatResponse.setId(id);
        return chatResponse.getId() != null;
    }

    @Override
    // Optimized Setter for object
    public boolean setObject(String object) {
        chatResponse.setObject(object);
        return chatResponse.getObject() != null;
    }

    @Override
    // Optimized Setter for created
    public boolean setCreated(Integer created) {
        chatResponse.setCreated(created);
        return chatResponse.getCreated() != null;
    }

    @Override
    // Optimized Setter for model
    public boolean setModel(String model) {
        chatResponse.setModel(model);
        return chatResponse.getModel() != null;
    }

    @Override
    // Optimized Setter for usage
    public boolean setUsage(Usage usage) {
        chatResponse.setUsage(usage);
        return chatResponse.getUsage() != null;
    }

    @Override
    // Optimized Setter for choices
    public boolean setChoices(List<Choice> choices) {
        chatResponse.setChoices(choices);
        return chatResponse.getChoices() != null && !chatResponse.getChoices().isEmpty();
    }

    @Override
    // Getter for choices
    public List<Choice> getChoices() {
        return chatResponse.getChoices();
    }
    @Override
    // Getter for usage
    public Usage getUsage() {
        return chatResponse.getUsage();
    }

}
