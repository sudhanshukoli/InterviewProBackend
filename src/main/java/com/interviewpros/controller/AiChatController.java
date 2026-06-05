package com.interviewpros.controller;

import com.interviewpros.dto.AiDto.AiFeedbackDto;
import com.interviewpros.dto.AiDto.AiQuestionDto;
import com.interviewpros.dto.AiDto.EvaluateAnsDto;
import com.interviewpros.service.AiServices.OllamaAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AiChatController {

        private final OllamaAiService ollamaAiService;

        @PostMapping("/getQuestions")
        public String getQuestions(@RequestBody AiQuestionDto aiQuestionDto) {
            return ollamaAiService.askAI(aiQuestionDto);
        }

    @PostMapping("/getFeedback")
    public AiFeedbackDto getFeedback(@RequestBody EvaluateAnsDto evaluateAnsDto) {

//            String question = "What is the difference between @Service and @Component annotations in a Spring Boot application, and when would you use each?";
//            String answer = "@Component creates bean of a class at the time spring configuration";

        return ollamaAiService.evaluateAnswer(evaluateAnsDto.getQuestion(), evaluateAnsDto.getAnswer());
    }
}
