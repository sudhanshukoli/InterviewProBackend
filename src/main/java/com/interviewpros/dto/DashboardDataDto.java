package com.interviewpros.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardDataDto {

    private Long sessionsDone;
    private Double avgScore;
    private Long techStacks;

}
