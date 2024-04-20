package com.example.notifications.impl;

import com.example.notifications.Dto.ProfileDataDto;
import com.example.notifications.Repository.ProfileDataRepo;
import com.example.notifications.Services.ProfileDataService;
import com.example.notifications.models.ProfileData;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.notifications.Exception.ErrorCode.PROFILE_DATA_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileDataServiceImpl implements ProfileDataService {

    private final ProfileDataRepo profileDataRepo ;
    private final ModelMapper modelMapper ;

    public List<ProfileDataDto> findAllProfileData() {
        List<ProfileData> profileData = profileDataRepo.findAll();
        return profileData.stream()
                .map(u -> modelMapper.map(u, ProfileDataDto.class))
                .collect(Collectors.toList());
    }

    public Optional<ProfileDataDto> findProfileDataById(Long id) {
        Optional<ProfileData> profileData = profileDataRepo.findById(id);
        return Optional.ofNullable(profileData.map(u -> modelMapper.map(u, ProfileDataDto.class)).orElseThrow(() -> new EntityNotFoundException("profileData " + id + "not found" + PROFILE_DATA_NOT_FOUND)));
    }

    public Optional<ProfileDataDto> findProfileDataByInvestorId(Long id) {
        Optional<ProfileData> profileData = profileDataRepo.findByUserId(id);
        return Optional.ofNullable(profileData.map(u -> modelMapper.map(u, ProfileDataDto.class)).orElseThrow(() -> new EntityNotFoundException("profileData " + id + "not found" + PROFILE_DATA_NOT_FOUND)));
    }

    public ProfileDataDto addProfileData(ProfileDataDto profileDataDto) {
        ProfileData profileData= modelMapper.map(profileDataDto, ProfileData.class);
        ProfileData savedProfileData = profileDataRepo.save(profileData);
        return modelMapper.map(savedProfileData, ProfileDataDto.class);
    }

    public void DeleteById(Long id) {
        if (id.equals(0)) log.error("ProfileDataId is null");
        else
        profileDataRepo.deleteById(id);
    }

}
