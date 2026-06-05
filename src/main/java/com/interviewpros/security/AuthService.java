package com.interviewpros.security;

import com.interviewpros.dto.LoginRequestDto;
import com.interviewpros.dto.LoginResponseDto;
import com.interviewpros.dto.SignUpRequestDto;
import com.interviewpros.entity.UserProfile;
import com.interviewpros.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDto login(LoginRequestDto loginRequestDto){

        System.out.println("INSIDE AUTH SERVICE LOGIN METHOD " + loginRequestDto.getUsername());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );

        UserProfile userProfile = userProfileRepository.findByUsername(loginRequestDto.getUsername())
                                        .orElseThrow(() -> new RuntimeException("User profile not found"));

        return new LoginResponseDto(userProfile.getId(), userProfile.getUsername(), userProfile.getRole());
    }


    public LoginResponseDto signup(SignUpRequestDto signUpRequestDto) {

        UserProfile userProfile = userProfileRepository.findByUsername(signUpRequestDto.getUsername()).orElse(null);

        if (userProfile != null){
            throw new IllegalArgumentException("Username Already Exists!");
        }

        userProfile = userProfileRepository.save(UserProfile
                        .builder()
                        .name(signUpRequestDto.getName())
                        .username(signUpRequestDto.getUsername())
                        .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                        .isActive(true)
                        .role("ADMIN")
                        .build());

        return new LoginResponseDto(userProfile.getId(), userProfile.getUsername(), userProfile.getRole());

    }
}
