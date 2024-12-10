package com.wzn.expensetrackerv2.service.botService;

import com.wzn.expensetrackerv2.dto.Choice;
import com.wzn.expensetrackerv2.dto.Usage;
import java.util.List;

public interface ChatResponseService {

    boolean setId(String id);
    boolean setObject(String object);
    boolean setCreated(Integer created);
    boolean setModel(String model);
    boolean setUsage(Usage usage);
    boolean setChoices(List<Choice> choices);

    List<Choice> getChoices();

    // Getter for usage
    Usage getUsage();
}
