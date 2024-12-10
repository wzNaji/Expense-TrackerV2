package com.wzn.expensetrackerv2.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "object",
        "created",
        "model",
        "choices",
        "usage"
})
@Data
@NoArgsConstructor
public class ChatResponse { // Modtage og håndtere data fra JSON-response fra OpenAI API kald.

    @JsonProperty("id")
    private String id;
    @JsonProperty("object")
    private String object; // Hvilken type af data den indeholder. fx. "user" så ved vi responsen indeholder data for en user.
    @JsonProperty("created")
    private Integer created; // Indeholder en tidsstempelværdi for hvornår objektet blev oprettet.
    @JsonProperty("model")
    private String model;
    @JsonProperty("usage")
    private Usage usage; // Dette felt indeholder information om, hvordan den aktuelle anmodning påvirker brugerkvoten eller ressourceforbruget, som f.eks. hvor mange tokens der er blevet brugt.
    @JsonProperty("choices")
    private List<Choice> choices; // Repræsenterer de mulige svar eller outputs genereret af modellen
    @JsonProperty("usage")
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();
}