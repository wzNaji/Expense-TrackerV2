package com.wzn.expensetrackerv2.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "prompt_tokens",
        "completion_tokens",
        "total_tokens",
        "prompt_tokens_details",
        "completion_tokens_details"
})

@Data
@NoArgsConstructor
public class Usage {

    @JsonProperty("prompt_tokens")
    private Integer promptTokens; // Dette felt repræsenterer antallet af tokens, der bruges til at generere det prompt, der sendes til modellen
    @JsonProperty("completion_tokens")
    private Integer completionTokens; // Dette felt angiver antallet af tokens, der genereres af modellen som respons på prompten.
    @JsonProperty("total_tokens")
    private Integer totalTokens; // Repræsenterer det samlede antal af prompt_tokens og completion_tokens.
    @JsonProperty("prompt_tokens_details")
    private PromptTokensDetails promptTokensDetails; // Opbevarer mere detaljeret information om de tokens, der er forbundet med prompten
    @JsonProperty("completion_tokens_details")
    private CompletionTokensDetails completionTokensDetails; // Ligesom prompt_tokens_details er denne klasse designet til at indeholde detaljeret information om de tokens, der udgør modellens respons (completion).
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();
}
