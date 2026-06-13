package com.interviewpros.controller;

import com.interviewpros.dto.DashboardDataDto;
import com.interviewpros.dto.LoginResponseDto;
import com.interviewpros.dto.UserDashboardDto;
import com.interviewpros.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/result")
@CrossOrigin(origins = {"http://localhost:5173", "https://interview-pro-by-sudhanshu.vercel.app"})
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/get-dashboard-data")
    public ResponseEntity<DashboardDataDto> getDashboardData(@RequestParam Long userId){

        System.out.println(dashboardService.getDashboardData(userId));

       return  ResponseEntity.ok(dashboardService.getDashboardData(userId));
    }

    @PostMapping("/pass-result")
    public boolean passResult(@RequestBody UserDashboardDto userDashboardDto){

        return dashboardService.passResult(userDashboardDto.getTechStack(), userDashboardDto.getScore(), LocalDate.now(), userDashboardDto.getUserId());

    }

}
