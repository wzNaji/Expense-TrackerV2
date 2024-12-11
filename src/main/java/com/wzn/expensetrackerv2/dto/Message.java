package com.wzn.expensetrackerv2.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@Component
@NoArgsConstructor
public class Message {

    @JsonProperty("role")
    private String role; // Dette felt angiver den rolle eller funktion, en person eller enhed spiller i den kontekst, som dataene vedrører.
    @JsonProperty("content")
    private String content; // Dette felt indeholder det faktiske indhold eller data, der er forbundet med et specifikt element eller en besked i systemet.
    @JsonProperty("refusal")
    private Object refusal; // Beskrivelse: refusal kunne være et objekt, der indeholder detaljer eller metadata om en eventuel afvisning eller negativ respons relateret til den overordnede operation.
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    public Message(String role, String content) {
        this.role = role;
        this.content = content;
    }
}
