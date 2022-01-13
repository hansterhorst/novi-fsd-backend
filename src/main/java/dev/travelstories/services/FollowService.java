package dev.travelstories.services;

import dev.travelstories.entities.Follow;
import dev.travelstories.entities.User;
import dev.travelstories.exceptions.BadRequestException;
import dev.travelstories.exceptions.RecordNotFoundException;
import dev.travelstories.repositories.FollowRepository;
import dev.travelstories.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FollowService {

   private final FollowRepository followRepository;
   private final UserRepository userRepository;

   @Autowired
   public FollowService(FollowRepository followRepository, UserRepository userRepository) {
      this.followRepository = followRepository;
      this.userRepository = userRepository;
   }


   /*
    * CRUD OPERATIONS
    * */

   //   POST a new follow user
   public Follow createFollowUser(Long userId, Long authUserId) {

      User user = userRepository.findById(userId).orElseThrow(() ->
         new RecordNotFoundException(String.format("User with id: %s not found.", userId)));

      User authUser = userRepository.findById(authUserId).orElseThrow(() ->
         new RecordNotFoundException(String.format("Follow user with id: %s not found.", authUserId)));

      if (user.getId().equals(authUser.getId())) {
         throw new BadRequestException("Can not follow yourself");
      }

      Follow follow = new Follow();
      follow.setAuthUserId(authUser.getId());
      follow.setFollowUser(user);

      return followRepository.save(follow);

   }


   //   GET all follow users
   public List<Follow> getAllFollowers(Long userId) {

      User user = userRepository.findById(userId).orElseThrow(() ->
         new RecordNotFoundException(String.format("User with id: %s not found.", userId)));

      return followRepository.findByFollowUserId(user.getId()).orElseThrow(() ->
         new RecordNotFoundException(String.format("User with id %s not found", user.getId())));
   }


   //   DELETE follow user
   public void deleteFollowByUserId(Long userId, Long authUserId) {

      User user = userRepository.findById(userId).orElseThrow(() ->
         new RecordNotFoundException(String.format("User with id: %s not found.", userId)));

      User authUser = userRepository.findById(authUserId).orElseThrow(() ->
         new RecordNotFoundException(String.format("User with id: %s not found.", authUserId)));

      List<Follow> follows = followRepository.findByFollowUserId(user.getId()).orElseThrow(() ->
         new RecordNotFoundException(String.format("User with id %s not found", user.getId())));

      for (Follow follow : follows) {
         if (follow.getAuthUserId().equals(authUser.getId())) {
            followRepository.delete(follow);
         }
      }
   }
}