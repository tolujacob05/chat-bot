package com.tolujacob.chatbot.config;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Configuration
public class ChatConfig {

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    @Bean
    @Qualifier("openaiRestTemplate")
    public RestTemplate openaiRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add(AUTHORIZATION, "Bearer " + apiKey);
            return execution.execute(request, body);
        });

        return restTemplate;
    }
}
