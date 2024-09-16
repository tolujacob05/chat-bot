package com.tolujacob.chatbot.chat;


import com.tolujacob.chatbot.dto.ChatGptRequest;
import com.tolujacob.chatbot.dto.ChatGptResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class ChatController {

    @Autowired
    @Qualifier("openaiRestTemplate")
    private RestTemplate restTemplate;

    @Value("${spring.ai.openai.model}")
    private String model;

    @Value("${spring.ai.openai.endpoint}")
    private String apiUrl;

    @GetMapping("/chat")
    public String chat(
            @RequestParam("prompt") String prompt
    ) {
        ChatGptRequest request = new ChatGptRequest(model, prompt);
        ChatGptResponse response = restTemplate.postForObject(
                apiUrl,
                request,
                ChatGptResponse.class
        );

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "No response";
        }

        return response.getChoices().get(0).getMessage().getContent();
    }
}
