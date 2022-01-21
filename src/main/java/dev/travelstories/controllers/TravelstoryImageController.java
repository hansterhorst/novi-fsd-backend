package dev.travelstories.controllers;

import dev.travelstories.services.TravelstoryImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static dev.travelstories.constants.Constants.AUTHORITY_ACCESS_URL;
import static dev.travelstories.constants.Constants.PUBLIC_ACCESS_URL;

@RestController
public class TravelstoryImageController {

   private final TravelstoryImageService travelstoryImageService;

   @Autowired
   public TravelstoryImageController(TravelstoryImageService travelstoryImageService) {
      this.travelstoryImageService = travelstoryImageService;
   }

   /*
    * CRUD
    * */

   //   upload a travelstory image
   @PostMapping(path = AUTHORITY_ACCESS_URL + "/user/{userId}/travelstory/{travelstoryId}/images/upload",
           consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
           produces = MediaType.APPLICATION_JSON_VALUE
   )
   @CrossOrigin(value = "http://localhost:3000")
   public void uploadTravelstoryImages(@PathVariable(value = "userId") Long userId,
                                       @PathVariable(value = "travelstoryId") Long travelstoryId,
                                       @RequestParam(value = "file") MultipartFile file) {

      travelstoryImageService.uploadTravelstoryImages(userId, travelstoryId, file);
   }

   //   download a travelstory image
   @GetMapping(path = PUBLIC_ACCESS_URL + "/user/{userId}/travelstory/{travelstoryId}/images/download")
   @CrossOrigin(value = "http://localhost:3000")
   public byte[] downloadUserProfileImages(@PathVariable(value = "userId") Long userId,
                                           @PathVariable(value = "travelstoryId") Long travelstoryId) {
      return travelstoryImageService.downloadTravelstoryImages(userId, travelstoryId);
   }

}