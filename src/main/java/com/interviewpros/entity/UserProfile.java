package com.interviewpros.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user_profiles")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String username;

    private String password;

    private String role;

    @Column(nullable = false)
    private Boolean isActive;

    @OneToMany(mappedBy = "userProfile")
    private List<UserDashboard> userDashboard;

}
