package dev.travelstories.controllers;

import dev.travelstories.dtos.TravelstoryDTO;
import dev.travelstories.entities.Travelstory;
import dev.travelstories.exceptions.BadRequestException;
import dev.travelstories.services.TravelstoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static dev.travelstories.constants.Constants.*;

@RestController
@CrossOrigin(value = "http://localhost:3000")
public class TravelstoryController {

   private final TravelstoryService travelstoryService;


   @Autowired
   public TravelstoryController(TravelstoryService travelstoryService) {
      this.travelstoryService = travelstoryService;
   }


   //   CREATE a new travelstory
   @PostMapping(path = AUTHORITY_USER_ACCESS_URL + "/travelstories/user/{userId}")
   public ResponseEntity<TravelstoryDTO> createTravelstory(@PathVariable(value = "userId") Long userId,
                                                           @Valid @RequestBody TravelstoryDTO travelstoryDTO) {

      Travelstory travelstory = TravelstoryDTO.dtoToEntity(travelstoryDTO);

      Travelstory newTravelstory = travelstoryService.createTravelstory(userId, travelstory);

      return new ResponseEntity<>(TravelstoryDTO.entityToDTO(newTravelstory), HttpStatus.CREATED);
   }


   //   GET all travelstories
   @GetMapping(path = AUTHORITY_USER_ACCESS_URL + "/travelstories")
   public ResponseEntity<List<TravelstoryDTO>> getAllTravelstories() {

      List<Travelstory> travelstoryList = travelstoryService.getAllTravelstories();

      List<TravelstoryDTO> travelstoryDTOList = travelstoryList.stream().map(TravelstoryDTO::entityToDTO).toList();

      return new ResponseEntity<>(travelstoryDTOList, HttpStatus.OK);
   }


   //   GET all travelstories by userId
   @GetMapping(path = AUTHORITY_USER_ACCESS_URL + "/travelstories/user/{userId}")
   public ResponseEntity<List<TravelstoryDTO>> getAllTravelstoriesByUserId(@PathVariable(value = "userId") Long userId) {

      List<Travelstory> travelstoryList = travelstoryService.getAllTravelstoriesByUserId(userId);

      List<TravelstoryDTO> travelstoryDTOList = travelstoryList.stream().map(TravelstoryDTO::entityToDTO).toList();

      return new ResponseEntity<>(travelstoryDTOList, HttpStatus.OK);
   }


   //   GET travelstory by id
   @GetMapping(path = AUTHORITY_USER_ACCESS_URL + "/travelstories/{travelstoryId}")
   public ResponseEntity<TravelstoryDTO> getTravelstoryById(@PathVariable(value = "travelstoryId") Long travelstoryId) {

      return new ResponseEntity<>(travelstoryService.getTravelstoryById(travelstoryId), HttpStatus.OK);
   }


   //   UPDATE travelstory by id
   @PutMapping(path = AUTHORITY_USER_ACCESS_URL + "/travelstories/{travelstoryId}")
   public ResponseEntity<TravelstoryDTO> updateTravelstoryById(@PathVariable(value = "travelstoryId") Long travelstoryId,
                                                               @Valid @RequestBody TravelstoryDTO travelstoryDTO) {

      Travelstory travelstory = TravelstoryDTO.dtoToEntity(travelstoryDTO);

      Travelstory updatedTravelstory = travelstoryService.updateTravelstoryById(travelstoryId, travelstory);

      return new ResponseEntity<>(TravelstoryDTO.entityToDTO(updatedTravelstory), HttpStatus.OK);
   }


   //   DELETE travelstory by id
   @DeleteMapping(path = AUTHORITY_USER_ACCESS_URL + "/travelstories/{travelstoryId}")
   public ResponseEntity<String> deleteTravelstoryById(@PathVariable(value = "travelstoryId") Long travelstoryId) {

      return new ResponseEntity<>(travelstoryService.deleteTravelstoryById(travelstoryId), HttpStatus.OK);
   }


   //   GET all public travelstories
   @GetMapping(path = PUBLIC_ACCESS_URL + "/travelstories")
   public ResponseEntity<List<TravelstoryDTO>> getAllPublicTravelstories() {

      List<Travelstory> travelstoryList = travelstoryService.getAllPublicTravelstories();

      List<TravelstoryDTO> travelstoryDTOList = travelstoryList.stream().map(TravelstoryDTO::entityToDTO).toList();

      return new ResponseEntity<>(travelstoryDTOList, HttpStatus.OK);
   }


   //   GET public travelstory by id
   @GetMapping(path = PUBLIC_ACCESS_URL + "/travelstories/{travelstoryId}")
   public ResponseEntity<TravelstoryDTO> getPublicTravelstoryById(@PathVariable(value = "travelstoryId") Long travelstoryId) {

      List<Travelstory> travelstoryList = travelstoryService.getAllPublicTravelstories();

      Travelstory travelstory = new Travelstory();
      for (Travelstory story : travelstoryList) {
         if (story.getId().equals(travelstoryId)) {
            travelstory = story;
         } else {
            throw new BadRequestException(String.format("Travelstory with id: %s is not public", travelstoryId));
         }
      }

      return new ResponseEntity<>(TravelstoryDTO.entityToDTO(travelstory), HttpStatus.OK);
   }
}
