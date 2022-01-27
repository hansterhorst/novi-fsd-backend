package dev.travelstories.controllers;

import dev.travelstories.services.UserProfileImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static dev.travelstories.constants.Constants.AUTHORITY_USER_ACCESS_URL;
import static dev.travelstories.constants.Constants.PUBLIC_ACCESS_URL;

@RestController
public class UserProfileImageController {

   private final UserProfileImageService userProfileImageService;

   @Autowired
   public UserProfileImageController(UserProfileImageService userProfileImageService) {
      this.userProfileImageService = userProfileImageService;
   }


   @PostMapping(
           path = AUTHORITY_USER_ACCESS_URL + "/user/{userId}/profile-image/upload",
           consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
           produces = MediaType.APPLICATION_JSON_VALUE
   )
   @CrossOrigin(value = "http://localhost:3000")
   public void uploadUserProfileImage(@PathVariable(value = "userId") Long userId,
                                      @RequestParam(value = "file") MultipartFile file) {
      userProfileImageService.uploadUserProfileImage(userId, file);
   }


   @GetMapping(path = PUBLIC_ACCESS_URL + "/user/{userId}/profile-image/download")
   @CrossOrigin(value = "http://localhost:3000")
   public byte[] downloadUserProfileImage(@PathVariable(value = "userId") Long userId) {
      return userProfileImageService.downloadUserProfileImage(userId);
   }


}
