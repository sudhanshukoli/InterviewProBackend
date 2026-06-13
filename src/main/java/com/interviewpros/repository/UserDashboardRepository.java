package com.interviewpros.repository;

import com.interviewpros.dto.DashboardDataDto;
import com.interviewpros.entity.UserDashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDashboardRepository extends JpaRepository<UserDashboard, Long> {

    @Query("SELECT new com.interviewpros.dto.DashboardDataDto( COUNT(u) , AVG(u.score) , COUNT(DISTINCT u.techStack) ) FROM UserDashboard u WHERE u.userProfile.id = :userId")
    DashboardDataDto getDashboardData(@Param("userId") Long userId);

}
