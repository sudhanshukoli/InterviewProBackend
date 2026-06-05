package com.interviewpros.service;

import com.interviewpros.entity.UserProfile;
import com.interviewpros.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserProfileRepository userProfileRepository;

    @Autowired
    public CustomUserDetailsService (UserProfileRepository userProfileRepository){
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserProfile userProfile = userProfileRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));

        return User.withUsername(userProfile.getUsername())
                .password(userProfile.getPassword())
                .roles(userProfile.getRole())
                .build();
    }
}
