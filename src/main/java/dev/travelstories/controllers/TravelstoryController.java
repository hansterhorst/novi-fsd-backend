package dev.travelstories.controllers;

import dev.travelstories.dtos.TravelstoryDTO;
import dev.travelstories.entities.Travelstory;
import dev.travelstories.services.TravelstoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/travelstories")
public class TravelstoryController {

   private final TravelstoryService travelstoryService;

   /*
    * CONSTRUCTOR
    * */
   @Autowired
   public TravelstoryController(TravelstoryService travelstoryService) {
      this.travelstoryService = travelstoryService;
   }


   /*
    * CRUD operations
    * */

   //   CREATE a new travelstory
   @PostMapping(path = "/{userId}")
   public ResponseEntity<TravelstoryDTO> createTravelstory(@PathVariable(value = "userId") Long userId,
                                                           @RequestBody TravelstoryDTO travelstoryDTO) {

      Travelstory travelstory = TravelstoryDTO.dtoToEntity(travelstoryDTO);

      Travelstory newTravelstory = travelstoryService.createTravelstory(userId, travelstory);

      return new ResponseEntity<>(TravelstoryDTO.entityToDTO(newTravelstory), HttpStatus.CREATED);
   }


   //   GET all travelstories
   @GetMapping
   public ResponseEntity<List<TravelstoryDTO>> getAllTravelstories() {

      List<Travelstory> travelstoryList = travelstoryService.getAllTravelstories();
      List<TravelstoryDTO> travelstoryDTOList = travelstoryList.stream().map(TravelstoryDTO::entityToDTO).toList();

      return new ResponseEntity<>(travelstoryDTOList, HttpStatus.OK);
   }


   //   GET travelstory by id
   @GetMapping(path = "/{travelstoryId}")
   public ResponseEntity<TravelstoryDTO> getTravelstoryById(@PathVariable(value = "travelstoryId") Long travelstoryId) {

      Travelstory travelstory = travelstoryService.getTravelstoryById(travelstoryId);

      return new ResponseEntity<>(TravelstoryDTO.entityToDTO(travelstory), HttpStatus.OK);
   }


   //   UPDATE travelstory by id
   @PutMapping(path = "/{travelstoryId}")
   public ResponseEntity<TravelstoryDTO> updateTravelstoryById(@PathVariable(value = "travelstoryId") Long travelstoryId,
                                                               @RequestBody TravelstoryDTO travelstoryDTO) {

      Travelstory travelstory = TravelstoryDTO.dtoToEntity(travelstoryDTO);

      Travelstory updatedTravelstory = travelstoryService.updateTravelstoryById(travelstoryId, travelstory);

      return new ResponseEntity<>(TravelstoryDTO.entityToDTO(updatedTravelstory), HttpStatus.OK);
   }


   //   DELETE travelstory by id
   @DeleteMapping(path = "/{travelstoryId}")
   public ResponseEntity<String> deleteTravelstoryById(@PathVariable(value = "travelstoryId") Long travelstoryId) {

      return new ResponseEntity<>(travelstoryService.deleteTravelstoryById(travelstoryId), HttpStatus.OK);
   }

}
