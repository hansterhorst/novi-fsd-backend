package dev.travelstories.services;

import dev.travelstories.entities.Travelstory;
import dev.travelstories.entities.User;
import dev.travelstories.exceptions.RecordNotFoundException;
import dev.travelstories.repositories.TravelstoryRepository;
import dev.travelstories.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TravelstoryService {

   private final TravelstoryRepository travelstoryRepository;
   private final UserRepository userRepository;


   /*
    * CONSTRUCTOR
    * */
   @Autowired
   public TravelstoryService(TravelstoryRepository travelstoryRepository, UserRepository userRepository) {

      this.travelstoryRepository = travelstoryRepository;
      this.userRepository = userRepository;

   }


   /*
    * CRUD operations
    * */

   //   POST a new travelstory
   public Travelstory createTravelstory(Long userId, Travelstory travelstory) {

      User user = userRepository.findById(userId).orElseThrow(() ->
         new RecordNotFoundException(String.format("User with id: %s not found.", userId)));

      travelstory.setAuthor(user.getFirstname() + " " + user.getLastname());
      travelstory.setUser(user);

      return travelstoryRepository.save(travelstory);
   }


   //   GET all travelstories
   public List<Travelstory> getAllTravelstories() {
      return travelstoryRepository.findAll();
   }


   //   GET travelstory by id
   public Travelstory getTravelstoryById(Long travelStoryId) {

      return travelstoryRepository.findById(travelStoryId).orElseThrow(() ->
         new RecordNotFoundException(String.format("Travelstory with id: %s not found.", travelStoryId)));
   }


//   GET all public travelstories
   public List<Travelstory> getAllPublicTravelstories(){
      return travelstoryRepository.findAllPublicTravelstories();
   }


   //   UPDATE travelstory by id
   public Travelstory updateTravelstoryById(Long travelstoryId, Travelstory travelstory) {

      Travelstory story = travelstoryRepository.findById(travelstoryId).orElseThrow(() ->
         new RecordNotFoundException(String.format("Travelstory with id: %s not found.", travelstoryId)));

      story.setTitle(travelstory.getTitle());
      story.setArticle(travelstory.getArticle());
      story.setAuthor(travelstory.getAuthor());
      story.setImageUrl(travelstory.getImageUrl());
      story.setCountry(travelstory.getCountry());
      story.setTripDate(travelstory.getTripDate());
      story.setTripType(travelstory.getTripType());
      story.setIsPublic(travelstory.getIsPublic());

      return travelstoryRepository.save(story);
   }


   //   DELETE travelstory by id
   public String deleteTravelstoryById(Long travelstoryId) {

      Travelstory travelstory = travelstoryRepository.findById(travelstoryId).orElseThrow(() ->
         new RecordNotFoundException(String.format("Travelstory with id: %s not found.", travelstoryId)));

      travelstoryRepository.delete(travelstory);

      return String.format("Travelstory with id: %s is successfully deleted.", travelstoryId);
   }


}
