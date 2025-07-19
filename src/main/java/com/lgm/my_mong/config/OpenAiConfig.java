package com.lgm.my_mong.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class OpenAiConfig {
    @Value("${spring.openai.api.key}")
    private String apiKey;

    @Value("${spring.openai.api.url}")
    private String apiUrl;

    @Bean
    public RestClient openAiRestClient() {
        return RestClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
