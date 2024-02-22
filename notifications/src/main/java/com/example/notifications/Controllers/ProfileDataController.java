package com.example.notifications.Controllers;

import com.example.notifications.Dto.ProfileDataDto;
import com.example.notifications.impl.ProfileDataServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/profileData")
@RequiredArgsConstructor
public class ProfileDataController {

    private final ProfileDataServiceImpl profileDataService;

    @GetMapping("/find/{id}")

    public ResponseEntity<ProfileDataDto> FindProfileData(@PathVariable("id") Long id) {
        Optional<ProfileDataDto> profileDataDto = profileDataService.findProfileDataById(id);
        if (profileDataDto.isPresent()) {
            return new ResponseEntity<>(profileDataDto.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addProfileData")

    public ResponseEntity<ProfileDataDto> addProfileData(@RequestBody ProfileDataDto profileDataDto, HttpServletResponse response) {
        ProfileDataDto newProfileData = profileDataService.addProfileData(profileDataDto);
        return new ResponseEntity<>(newProfileData, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")

    public ResponseEntity<?> DeleteUser(@PathVariable("id") Long id) {
        if (!profileDataService.findProfileDataById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            profileDataService.DeleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
