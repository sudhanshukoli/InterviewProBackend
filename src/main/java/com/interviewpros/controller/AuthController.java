package com.interviewpros.controller;

import com.interviewpros.dto.LoginRequestDto;
import com.interviewpros.dto.LoginResponseDto;
import com.interviewpros.dto.SignUpRequestDto;
import com.interviewpros.security.AuthService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:5173", "https://interview-pro-by-sudhanshu.vercel.app"})
public class AuthController {


    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/check")
    public String getLogin(){
        return "<h2>Sudhanshu Here</h2>";
    }

    @PostConstruct
    public void init(){
        System.out.println("Inside init --------------");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> getUsers(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<LoginResponseDto> signupUser(@RequestBody SignUpRequestDto signUpRequestDto){
        return ResponseEntity.status(201).body(authService.signup(signUpRequestDto));
    }

}
