package com.lgm.my_mong.provider;

import com.lgm.my_mong.dto.OpenAiResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpenAiProvider {

    private final RestClient openAiRestClient;

    @Value("${spring.openai.api.model}")
    private String model;

    @Value("${spring.openai.api.max-tokens}")
    private int maxTokens;

    @Value("${spring.openai.api.temperature}")
    private float temperature;

    public OpenAiResponseDTO sendRequest(String systemMessage, String userMessage) {
        Map<String, Object> request = Map.of(
                "model", model,
                "messages", List.of(
                        Map.of("role", "system", "content", systemMessage),
                        Map.of("role", "user", "content", userMessage)
                ),
                "max_tokens", maxTokens,
                "temperature", temperature
        );

        return openAiRestClient
                .post()
                .uri("/chat/completions")
                .body(request)
                .retrieve()
                .body(OpenAiResponseDTO.class);
    }
}