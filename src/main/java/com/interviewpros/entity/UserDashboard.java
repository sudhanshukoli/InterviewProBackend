package com.interviewpros.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDashboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String techStack;

    private int score;

    private LocalDate interviewDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserProfile userProfile;

}
