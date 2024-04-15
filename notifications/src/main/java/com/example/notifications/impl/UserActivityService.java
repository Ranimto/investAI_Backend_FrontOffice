package com.example.notifications.impl;

import com.example.notifications.Dto.InvestmentDto;
import com.example.notifications.Dto.ProfileDataDto;
import com.example.notifications.Dto.UserActivityDto;
import com.example.notifications.Repository.UserActivityRepo;
import com.example.notifications.models.Investment;
import com.example.notifications.models.ProfileData;
import com.example.notifications.models.UserActivity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.notifications.Exception.ErrorCode.PROFILE_DATA_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserActivityService {
    private final UserActivityRepo userActivityRepository;
    private final ModelMapper modelMapper ;

    public List<UserActivityDto> getUserActivitiesByUserId(Long userId) {
        List<UserActivity> userActivities = userActivityRepository.findByUserId(userId);
        return userActivities.stream().map(u-> modelMapper.map(u, UserActivityDto.class)).collect(Collectors.toList()) ;
    }

    public UserActivityDto saveUserActivity(UserActivityDto userActivityDto) {
        UserActivity userActivity=modelMapper.map(userActivityDto,UserActivity.class);
        UserActivity savedUserActivity= userActivityRepository.save(userActivity);
        return modelMapper.map(savedUserActivity,UserActivityDto.class);
    }

}
