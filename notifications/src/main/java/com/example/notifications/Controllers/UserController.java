package com.example.notifications.Controllers;

import com.example.notifications.Dto.UserDto;

import com.example.notifications.Exception.ApiException;
import com.example.notifications.impl.UserServiceImpl;
import com.example.notifications.models.AppUser;
import io.jsonwebtoken.io.IOException;
import jakarta.mail.Quota;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {


    private final UserServiceImpl userService;
    private  final  String uploadDir="src/main/resources/imagesstock/" ;

    @GetMapping("/allUsers")
    //@PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.findAllUser();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    //@PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<UserDto> FindUser(@PathVariable("id") Long id) {
        Optional<UserDto> userDto = userService.findUserById(id);
        if (userDto.isPresent()) {
            return new ResponseEntity<>(userDto.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findByEmail/{email}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> findUserByEmail(@PathVariable("email") String email) {
        UserDto userDto = userService.findUserByEmail(email);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/findByVerificationCode/{verificationCode}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> findByVerificationCode(@PathVariable("verificationCode") String verificationCode) {
        UserDto userDto = userService.findUserByVerificationCode(verificationCode);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    //ADMIN token : eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJUb3VtaUBnbWFpbC5jb20iLCJpYXQiOjE3MDEzOTAwOTAsImV4cCI6MTcwMTM5MTUzMH0.CIY3scC9Sb09QM2aBsrQ8RGqwbvYJdPJcyn8sk2-QDA
    @PostMapping("/addUser")
    //@PreAuthorize("hasRole('ADMIN')")

    //User doit être désérialisé (Conversion d'un objet en Json ou xml en objet Java )
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto, HttpServletResponse response) {
        UserDto newUser = userService.addUser(userDto);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    //@PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<UserDto> UpdateUser(@RequestBody UserDto userDto, HttpServletResponse response) {
        UserDto updateUser = userService.UpdateUser(userDto);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<?> DeleteUser(@PathVariable("id") Long id) {
        if (!userService.findUserById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            userService.DeleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
    @GetMapping("/isInvestor/{id}")
    public ResponseEntity<Boolean> isInvestor(@PathVariable("id") Long id) {
        boolean res = userService.isInvestor(id);
            return new ResponseEntity<Boolean>(res, HttpStatus.OK);
    }

    @PutMapping("update-profile/{userId}")
    public ResponseEntity<AppUser> updateUserProfile(@PathVariable Long userId, @RequestParam("profileImage") MultipartFile profileImage) {
        try {
            AppUser updatedUser = userService.updateUserProfile(userId, profileImage);
            return ResponseEntity.ok(updatedUser);

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating user profile", e);
             }
    }

   public  String determineContentType(String filename) {
        String[] parts=filename.split("\\.");
        if (parts.length>1){
            String extension =parts[parts.length-1].toLowerCase();
            switch (extension){
                case "jpg" :
                case "jpeg": return "images/jpeg" ;
                case "png": return "images/png";
                default: return  "images/*" ;
            }
        }

      return  "images/*" ;
   }
   @GetMapping("images/{filename}")
   public ResponseEntity<Resource> serveImage(@PathVariable String filename){
        try{
            Path imagePath= Paths.get(uploadDir).resolve((filename));
            Resource imageFile=new UrlResource(imagePath.toUri());

            if(imageFile.exists()&& imageFile.isReadable()){
                String contentType=determineContentType(filename);
                HttpHeaders headers=new HttpHeaders();
                headers.setContentType(org.springframework.http.MediaType.parseMediaType((contentType)));
                return ResponseEntity.ok().headers(headers).body(imageFile);
                }
            else{
                return  ResponseEntity.notFound().build();
            }

        } catch (MalformedURLException e) {
            return ResponseEntity.status(500).body(null);
        }
   }





}
