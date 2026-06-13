package com.interviewpros.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class UserDashboardDto {

    private String techStack;
    private int score;
    private Long userId;

}
