package com.interviewpros.service;

import com.interviewpros.dto.DashboardDataDto;
import com.interviewpros.entity.UserDashboard;
import com.interviewpros.entity.UserProfile;
import com.interviewpros.repository.UserDashboardRepository;
import com.interviewpros.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserDashboardRepository userDashboardRepository;
    private final UserProfileRepository userProfileRepository;

    public boolean passResult(String techStack, int score, LocalDate interviewDate, Long userId){

        UserProfile userProfile = userProfileRepository.findById(userId).orElse(null);

        if (userProfile == null){
            return false;
        }

        userDashboardRepository.save(UserDashboard.builder()
                .interviewDate(interviewDate)
                .score(score)
                .techStack(techStack)
                .userProfile(userProfile)
                .build());

        return true;

    }

    public DashboardDataDto getDashboardData(Long userId){

        return userDashboardRepository.getDashboardData(userId);
    }

}
