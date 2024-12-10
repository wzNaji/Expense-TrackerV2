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
        "reasoning_tokens",
        "audio_tokens",
        "accepted_prediction_tokens",
        "rejected_prediction_tokens"
})

@Data
@NoArgsConstructor
public class CompletionTokensDetails {

    @JsonProperty("reasoning_tokens")
    private Integer reasoningTokens; // Dette felt kan repræsentere antallet af tokens, der bruges til at formulere logiske eller rationelle svar,
    @JsonProperty("audio_tokens")
    private Integer audioTokens; // Ikke nødvendigt for dette program, men dette felt kunne være relevant i kontekster, hvor der arbejdes med audio-genererende modeller.
    @JsonProperty("accepted_prediction_tokens")
    private Integer acceptedPredictionTokens; // Dette felt kunne tælle antallet af tokens, der er involveret i forudsigelser eller output, som systemet endeligt accepterer.
    @JsonProperty("rejected_prediction_tokens")
    private Integer rejectedPredictionTokens; // Dette felt angiver antallet af tokens, der er forbundet med forudsigelser eller dele af genereret indhold, som ikke blev accepteret eller inkluderet i det endelige output.
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();
}
