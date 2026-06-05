package com.interviewpros.service.AiServices;

import com.interviewpros.dto.AiDto.AiFeedbackDto;
import com.interviewpros.dto.AiDto.AiQuestionDto;
import com.interviewpros.dto.AiDto.QuestionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GroqAiServices {

    @Value("${GROQ_API_KEY}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public String askAI(AiQuestionDto aiQuestionDto) {

        String prompt = "Generate " + aiQuestionDto.getQuestions() +" interview question. tech stack : " + aiQuestionDto.getTechStack() +
                ". Difficulty : " + aiQuestionDto.getDifficulty() + ". Only output questions in Format:" +
                "[questions] (Array of questions comma separated with double-quoted for each question)," +
                "Do not add any explanation. Do not add numbers to questions.";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = Map.of(
                "model", "llama-3.1-8b-instant",
                "messages", List.of(
                        Map.of(
                                "role", "user",
                                "content", prompt
                        )
                )
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(
                        "https://api.groq.com/openai/v1/chat/completions",
                        request,
                        Map.class
                );

        Map<String, Object> body = response.getBody();

        List<Map<String, Object>> choices =
                (List<Map<String, Object>>) body.get("choices");

        Map<String, Object> message =
                (Map<String, Object>) choices.get(0).get("message");

        System.out.println("This is Feedback got from GROQ " + message.get("content"));

        String feedbackResponse = (String) message.get("content");

        return feedbackResponse;
    }

    public AiFeedbackDto evaluateAnswer(String question, String answer){

        System.out.println("Question - " + question +" answer " + answer);

        String prompt=" Question: " + question +
                "Candidate Answer: " + answer +
                " .Evaluate answer and Give OUTPUT ONLY IN JSON format - score (OUT OF 10) and feedback -" +
                "Format:" +
                "{" +
                "score: <score>," +
                "  feedback: <feedback>" +
                "}" +
                "Do NOT add any explanation outside the JSON DO NOT add any note also." +
                " I want strictly only mentioned feedback not extra data" +
                " Do NOT add any extra character";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = Map.of(
                "model", "llama-3.1-8b-instant",
                "messages", List.of(
                        Map.of(
                                "role", "user",
                                "content", prompt
                        )
                )
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(
                        "https://api.groq.com/openai/v1/chat/completions",
                        request,
                        Map.class
                );

        Map<String, Object> body = response.getBody();

        List<Map<String, Object>> choices =
                (List<Map<String, Object>>) body.get("choices");

        Map<String, Object> message =
                (Map<String, Object>) choices.get(0).get("message");



        System.out.println("This is Feedback got from GROQ" + message.get("content"));

        String feedbackResponse = (String) message.get("content");

        AiFeedbackDto aiFeedbackDto = objectMapper.readValue(feedbackResponse, AiFeedbackDto.class);

        System.out.println("This is Feedback after reading through object mapper" + aiFeedbackDto.getFeedback());

        return aiFeedbackDto;
    }

}
