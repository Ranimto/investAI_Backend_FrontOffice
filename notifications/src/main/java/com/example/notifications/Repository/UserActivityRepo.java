package com.example.notifications.Repository;

import com.example.notifications.Dto.UserActivityDto;
import com.example.notifications.models.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserActivityRepo extends JpaRepository<UserActivity,Long> {
    List<UserActivity> findByUserId(Long userId);
}
