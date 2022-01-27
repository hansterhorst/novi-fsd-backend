package dev.travelstories.controllers;

import dev.travelstories.dtos.FollowDTO;
import dev.travelstories.entities.Follow;
import dev.travelstories.services.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static dev.travelstories.constants.Constants.AUTHORITY_USER_ACCESS_URL;

@RestController
@RequestMapping(path = AUTHORITY_USER_ACCESS_URL)
@CrossOrigin(value = "http://localhost:3000")
public class FollowController {

   private final FollowService followService;

   @Autowired
   public FollowController(FollowService followService) {
      this.followService = followService;
   }


   //   CREATE a follow user
   @PostMapping(path = "/{userId}/follow/{authUserId}")
   public ResponseEntity<FollowDTO> createFollowUserByUserId(@PathVariable(value = "userId") Long userId,
                                                          @PathVariable(value = "authUserId") Long authUserId) {

      Follow followUser = followService.createFollowUser(userId, authUserId);

      return new ResponseEntity<>(FollowDTO.entityToDTO(followUser), HttpStatus.CREATED);
   }


   //   GET all follow users
   @GetMapping(path = "/{userId}/follow")
   public ResponseEntity<List<FollowDTO>> getAllFollowUsers(@PathVariable(value = "userId") Long userId) {

      List<Follow> follows = followService.getAllFollowers(userId);
      List<FollowDTO> followDTOList = follows.stream().map(FollowDTO::entityToDTO).toList();

      return new ResponseEntity<>(followDTOList, HttpStatus.OK);

   }


   //   DELETE follow user
   @DeleteMapping(path = "/{userId}/follow/{authUserId}")
   public ResponseEntity<String> deleteFollowUserById(@PathVariable(value = "userId") Long userId,
                                                      @PathVariable(value = "authUserId") Long authUserId) {

      followService.deleteFollowByUserId(userId, authUserId);

      return new ResponseEntity<>("Follow user successfully deleted", HttpStatus.OK);
   }

}
