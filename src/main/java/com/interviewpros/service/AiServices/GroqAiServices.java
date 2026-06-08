package com.interviewpros.service.AiServices;

import com.interviewpros.dto.AiDto.AiFeedbackDto;
import com.interviewpros.dto.AiDto.AiQuestionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GroqAiServices {

    @Value("${groq.api.key}")
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

        System.out.println("Question -------------- " + question +"---------- answer ------------ " + answer);

        String prompt = "You are an interviewer evaluating a candidate answer.\n" +
                        "Question: " + question + "\n" +
                        "Candidate Answer: " + answer + "\n\n" +
                        "Scoring Rules:\n" +
                        "- If answer is 'don't know', 'do not know', 'idk', empty, irrelevant, or contains no useful information: score = 0.\n" +
                        "- If the answer contains phrases like 'don't know', 'not sure', 'no idea', score MUST be 0.\n" +
                        "- If answer is partially correct: score between 1 and 5.\n" +
                        "- If answer is mostly correct but misses important details: score between 6 and 8.\n" +
                        "- If answer is accurate, complete, and technically correct: score between 9 and 10.\n\n" +

                        "Return ONLY valid JSON:\n" +
                        "{\"score\": <number>, \"feedback\": \"<feedback>\"}";

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
