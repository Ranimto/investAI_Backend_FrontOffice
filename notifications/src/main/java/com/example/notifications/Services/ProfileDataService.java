package com.example.notifications.Services;

import com.example.notifications.Dto.ProfileDataDto;

import java.util.List;
import java.util.Optional;

public interface ProfileDataService {

    List<ProfileDataDto> findAllProfileData();
    Optional<ProfileDataDto> findProfileDataById(Long id);
    Optional<ProfileDataDto> findProfileDataByInvestorId(Long id);
    ProfileDataDto addProfileData(ProfileDataDto profileDataDto);
    void DeleteById(Long id);
}
