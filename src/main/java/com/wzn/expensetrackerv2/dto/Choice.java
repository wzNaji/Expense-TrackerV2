package com.wzn.expensetrackerv2.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "index",
        "message",
        "logprobs",
        "finish_reason"
})

@Data
@Component
@NoArgsConstructor
public class Choice {

    @JsonProperty("index")
    private Integer index; // Angiver indekset eller positionen for dette specifikke valg i en liste af mulige valg eller svar genereret af API'et.
    @JsonProperty("message")
    private Message message;
    @JsonProperty("logprobs")
    private Object logprobs;
    @JsonProperty("finish_reason")
    private String finishReason; // forklarer grunden til, at responsen eller genereringen af teksten blev afsluttet. For eksempel kan grunde omfatte 'length' (nået maksimal længde)
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();
}
