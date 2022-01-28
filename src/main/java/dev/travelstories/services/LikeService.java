package dev.travelstories.services;

import dev.travelstories.entities.Like;
import dev.travelstories.entities.Travelstory;
import dev.travelstories.entities.User;
import dev.travelstories.exceptions.BadRequestException;
import dev.travelstories.exceptions.RecordNotFoundException;
import dev.travelstories.repositories.LikeRepository;
import dev.travelstories.repositories.TravelstoryRepository;
import dev.travelstories.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {

   private final LikeRepository likeRepository;
   private final TravelstoryRepository travelstoryRepository;
   private final UserRepository userRepository;


   @Autowired
   public LikeService(LikeRepository likeRepository, TravelstoryRepository travelstoryRepository, UserRepository userRepository) {
      this.likeRepository = likeRepository;
      this.travelstoryRepository = travelstoryRepository;
      this.userRepository = userRepository;
   }


   public void createLike(Long travelstoryId, Long userId) {

      Travelstory travelstory = getTravelstoryByIdOrElseThrow(travelstoryId);

      User user = userRepository.findById(userId).orElseThrow(() ->
              new RecordNotFoundException(String.format("User with id %s not found", userId)));

      Like like = new Like();
      like.setUserId(user.getId());
      like.setTravelstory(travelstory);

      likeRepository.save(like);
   }


   public List<Like> getAllTravelstoryLikes(Long travelstoryId) {

      Travelstory travelstory = getTravelstoryByIdOrElseThrow(travelstoryId);

      return likeRepository.findLikesByTravelstoryId(travelstory.getId()).orElseThrow(() ->
              new RecordNotFoundException(String.format("Travelstory with id %s not found", travelstoryId)));
   }


   public void deleteTravelstoryLikeByUserId(Long travelstoryId, Long userId) {

      Travelstory travelstory = getTravelstoryByIdOrElseThrow(travelstoryId);

      List<Like> likes = likeRepository.findLikesByTravelstoryId(travelstory.getId()).orElseThrow(() ->
              new RecordNotFoundException(String.format("Travelstory with id %s not found", travelstoryId)));

      User user = userRepository.findById(userId).orElseThrow(() ->
              new RecordNotFoundException(String.format("User with id %s not found", userId)));

      for (Like like : likes) {
         if (like.getUserId().equals(user.getId())) {
            likeRepository.delete(like);
         }
      }
   }



   private Travelstory getTravelstoryByIdOrElseThrow(Long travelstoryId) {
      return travelstoryRepository.findById(travelstoryId).orElseThrow(() ->
              new RecordNotFoundException(String.format("Travelstory with id %s not found", travelstoryId)));
   }
}
