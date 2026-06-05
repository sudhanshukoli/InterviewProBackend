package com.interviewpros.service.AiServices;

import com.interviewpros.dto.AiDto.AiFeedbackDto;
import com.interviewpros.dto.AiDto.AiQuestionDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OllamaAiService {

    private final RestTemplate restTemplate;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;

    String ollamaUrl = "http://localhost:11434/api/generate";

    public String askAI(AiQuestionDto aiQuestionDto) {

        String prompt = "Generate " + aiQuestionDto.getQuestions() +" interview question. tech stack : " + aiQuestionDto.getTechStack() +
                ". Difficulty : " + aiQuestionDto.getDifficulty() + ". Only output questions in Format:" +
                "questions: [<questions>] (Array of questions)," +
                "Do not add any explanation outside the JSON.";

        Map<String, Object> body = new HashMap<>();
        body.put("model", "llama3");
        body.put("prompt", prompt);
        body.put("stream", false);

        ResponseEntity<Map> response = restTemplate.postForEntity(ollamaUrl, body, Map.class);

        System.out.println("This is Feedback got from Ollama " + response.getBody().get("response"));

        return response.getBody().get("response").toString();
    }

    public AiFeedbackDto evaluateAnswer(String question, String answer){
        String prompt=" Question: " + question +
                        "Candidate Answer: " + answer +
                        " .Evaluate answer and Give OUTPUT ONLY IN JSON format - score (OUT OF 10) and feedback -" +
                        "Format:" +
                        "{" +
                        "score: <score>," +
                        "  feedback: <feedback>" +
                        "}" +
                        "Do NOT add any explanation outside the JSON DO NOT add any note also. make sure that property is also double-quoted." +
                        " Do NOT add any extra character";

        Map<String, Object> body = new HashMap<>();
        body.put("model", "llama3");
        body.put("prompt", prompt);
        body.put("stream", false);

        ResponseEntity<Map> response = restTemplate.postForEntity(ollamaUrl, body, Map.class);

        String feedbackResponse = (String) response.getBody().get("response");

        System.out.println("This is Feedback got from Ollama" + feedbackResponse);

        AiFeedbackDto aiFeedbackDto = objectMapper.readValue(feedbackResponse, AiFeedbackDto.class);

//        AiFeedbackDto aiFeedbackDto = modelMapper.map(response.getBody().get("response"), AiFeedbackDto.class);
//
//        System.out.println("This is Feedback got froom Ollama" + aiFeedbackDto.getFeedback());
//
//        return aiFeedbackDto;

        System.out.println("This is Feedback after reading through object mapper" + aiFeedbackDto.getFeedback());

        return aiFeedbackDto;
    }
}
