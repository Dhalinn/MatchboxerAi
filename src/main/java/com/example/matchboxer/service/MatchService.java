package com.example.matchboxer.service;

import com.example.matchboxer.dto.MatchRequest;
import com.example.matchboxer.dto.MatchResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

@Service
public class MatchService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public MatchService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public MatchResponse getBoxerMatch(MatchRequest request) throws JsonProcessingException {
        String prompt = buildPrompt(request);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> userMessage = Map.of("role", "user", "content", prompt);
        Map<String, Object> systemMessage = Map.of("role", "system", "content",
                "You are a professional boxing coach and analyst. Respond with JSON only.");

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-4o");
        body.put("messages", List.of(systemMessage, userMessage));
        body.put("temperature", 0.8);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "https://api.openai.com/v1/chat/completions",
                requestEntity,
                Map.class
        );


        Map<String, Object> message = (Map<String, Object>) ((List<?>) response.getBody().get("choices")).get(0);
        Map<String, Object> messageContent = (Map<String, Object>) message.get("message");
        String content = (String) messageContent.get("content");


        String rawJson = extractJsonFromCodeBlock(content);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(rawJson, MatchResponse.class);
    }

    private String buildPrompt(MatchRequest request) {
        return String.format("""
                A user wants to know which famous boxer they most resemble based on the following:
                Height: %d cm
                Weight: %d kg
                Reach: %d cm
                Strength: %s
                Weakness: %s

                Based on these stats, suggest the most suitable boxing style for this person — explain why it fits. Also explain why the listed weakness matters in their case. Provide a brief but personalized training plan that addresses the weakness and enhances the strength. Respond with a JSON object like::
                  {
                     "boxer": "Name",
                     "record": "e.g. 50-0",
                     "titles": "Multiple-time world champion",
                     "style": "Suggested style with reasoning (e.g., 'You suit a counter-punching style due to your fast hands and shorter reach.')",
                     "advice": "What the user should improve and why it affects their performance",
                     "trainingPlan": [
                     "Drill 1 – reason",
                     "Drill 2 – reason",
                     "Drill 3 – reason"
                    ]
                   }
               
                """, request.getHeight(), request.getWeight(), request.getReach(),
                request.getStrength(), request.getWeakness());
    }

    private String extractJsonFromCodeBlock(String input) {
        if (input.contains("```")) {
            int start = input.indexOf("```") + 3;
            int jsonStart = input.indexOf("{", start);
            int end = input.lastIndexOf("```");
            return input.substring(jsonStart, end).trim();
        }
        return input.trim();
    }
}
