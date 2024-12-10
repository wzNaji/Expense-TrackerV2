package com.wzn.expensetrackerv2.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.wzn.expensetrackerv2.dto.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


import java.util.List;

@Data
@NoArgsConstructor
@Component
@JsonInclude(JsonInclude.Include.NON_NULL) // Udelader null-værdier fra JSON respons.
@JsonPropertyOrder({ // Specificerer rækkefølgen på JSON serialiseringen.
        "model",
        "messages",
        "n",
        "temperature",
        "max_tokens",
        "stream",
        "presence_penalty"
})
public class ChatRequest {
    @JsonProperty("model")
    private String model; // Angiver hvilken model der vil bruges.
    @JsonProperty("messages")
    private List<Message> messages; // Hjælper botten med at forstå kontekst. "User" eller "System" kan hjælpe med at forstå hvem der siger hvad.
    @JsonProperty("n")
    private Integer n; // Antallet af svar der ønskes (hvis der fx. skal vælges imellem flere )
    @JsonProperty("temperature")
    private Integer temperature; // Tilfældigheden i svar. Kunne være en double, da det er fra 0-1.
    @JsonProperty("max_tokens")
    private Integer maxTokens; // Mængden af tokens man vil have genereret fra api. 1 token == 4 chars (ish)
    @JsonProperty("stream")
    private Boolean stream; // Hvis true kan genererede svar streames(vises) imens resten genereres.
    @JsonProperty("presence_penalty")
    private Integer presencePenalty; // Hvor meget botten skal undgå at repeat sig sig. Højere værdi kan gøre en samtale mere 'menneskelig'. 0.0 - 2.0
}
