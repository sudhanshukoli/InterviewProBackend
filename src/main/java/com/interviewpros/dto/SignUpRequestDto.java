package com.interviewpros.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SignUpRequestDto {

    private String name;
    private String username;
    private String password;

}
