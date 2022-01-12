package dev.travelstories.services;

import dev.travelstories.entities.Like;
import dev.travelstories.entities.Travelstory;
import dev.travelstories.exceptions.RecordNotFoundException;
import dev.travelstories.repositories.LikeRepository;
import dev.travelstories.repositories.TravelstoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {

   private final LikeRepository likeRepository;
   private final TravelstoryRepository travelstoryRepository;


   @Autowired
   public LikeService(LikeRepository likeRepository, TravelstoryRepository travelstoryRepository) {
      this.likeRepository = likeRepository;
      this.travelstoryRepository = travelstoryRepository;
   }


   public void createLike(Long travelstoryId, Long userId) {

      Travelstory travelstory = travelstoryRepository.findById(travelstoryId).orElseThrow(() ->
         new RecordNotFoundException(String.format("User with id %s not found", userId)));

      Like like = new Like();
      like.setUserId(userId);
      like.setTravelstory(travelstory);

      likeRepository.save(like);
   }


   public List<Like> getAllTravelstoryLikes(Long travelstoryId) {

      return likeRepository.findByTravelstoryId(travelstoryId).orElseThrow(() ->
         new RecordNotFoundException(String.format("Travelstory with id %s not found", travelstoryId)));
   }


   public void deleteLikeByUserId(Long travelstoryId, Long userId) {

      List<Like> likes = likeRepository.findByTravelstoryId(travelstoryId).orElseThrow(() ->
         new RecordNotFoundException(String.format("Travelstory with id %s not found", travelstoryId)));

      for (Like like : likes) {
         if (like.getUserId().equals(userId)) {
            likeRepository.delete(like);
         }
      }
   }

}
