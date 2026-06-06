package com.interviewpros.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoginResponseDto {

    private Long id;
    private String username;
    private String role;
    private String name;

}
