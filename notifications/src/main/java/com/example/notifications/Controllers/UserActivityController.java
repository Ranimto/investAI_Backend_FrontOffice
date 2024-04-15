package com.example.notifications.Controllers;

import com.example.notifications.Dto.ProfileDataDto;
import com.example.notifications.Dto.UserActivityDto;
import com.example.notifications.impl.UserActivityService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-activity")
public class UserActivityController {
    private  final UserActivityService userActivityService ;

    @GetMapping("/getUserActivity/{userId}")
    public ResponseEntity<List<UserActivityDto>>  getUserActivityByUserId(@PathVariable Long userId){
        List<UserActivityDto> userActivityList = userActivityService.getUserActivitiesByUserId(userId);
        return ResponseEntity.ok(userActivityList);
    }
    @PostMapping("/save")
    public ResponseEntity<UserActivityDto>  saveUserActivity(@RequestBody UserActivityDto userActivityDto){
        UserActivityDto savedUserActivity = userActivityService.saveUserActivity(userActivityDto);
        return ResponseEntity.ok(savedUserActivity);
    }

}
