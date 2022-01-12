package dev.travelstories.controllers;

import dev.travelstories.entities.Like;
import dev.travelstories.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/users/travelstory")
@CrossOrigin(value = "http://localhost:3000")
public class LikeController {

   private final LikeService likeService;

   @Autowired
   public LikeController(LikeService likeService) {
      this.likeService = likeService;
   }


   @PostMapping(path = "/{travelstoryId}/likes/user/{userId}")
   public ResponseEntity<String> createLike(@PathVariable(value = "travelstoryId") Long travelstoryId, @PathVariable(value = "userId") Long userId) {

      likeService.createLike(travelstoryId, userId);

      return new ResponseEntity<>("Like successfully added", HttpStatus.CREATED);
   }

   @GetMapping(path = "/{travelstoryId}/likes")
   public ResponseEntity<List<Like>> getAllTravelstoryLikes(@PathVariable(value = "travelstoryId") Long travelstoryId) {

      List<Like> likes = likeService.getAllTravelstoryLikes(travelstoryId);

      return new ResponseEntity<>(likes, HttpStatus.OK);
   }


   @DeleteMapping(path = "/{travelstoryId}/likes/user/{userId}")
   public ResponseEntity<String> deleteLikeByUserId(@PathVariable(value = "travelstoryId") Long travelstoryId,
                                                    @PathVariable(value = "userId") Long userId) {

      likeService.deleteLikeByUserId(travelstoryId, userId);

      return new ResponseEntity<>("Like successfully deleted", HttpStatus.OK);
   }

}
