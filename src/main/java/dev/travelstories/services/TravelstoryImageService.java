package dev.travelstories.services;

import dev.travelstories.entities.Travelstory;
import dev.travelstories.entities.User;
import dev.travelstories.exceptions.BadRequestException;
import dev.travelstories.repositories.TravelstoryRepository;
import dev.travelstories.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.IMAGE_JPEG;
import static org.apache.http.entity.ContentType.IMAGE_PNG;

@Service
public class TravelstoryImageService {

   @Value("${aws-bucket-name}")
   private String awsBucketName;

   private final AwsImageStoreService awsImageStoreService;
   private final TravelstoryRepository travelstoryRepository;
   private final UserRepository userRepository;

   public TravelstoryImageService(AwsImageStoreService awsImageStoreService, TravelstoryRepository travelstoryRepository, UserRepository userRepository) {
      this.awsImageStoreService = awsImageStoreService;
      this.travelstoryRepository = travelstoryRepository;
      this.userRepository = userRepository;
   }


   /*
    * USER PROFILE IMAGE
    * */
   public void uploadTravelstoryImages(Long userId, Long travelstoryId, MultipartFile file) {

      // check if image not empty
      isFileEmptyOrThrow(file);

      // if file is an image
      isImageOrThrow(file);

      // check travelstory and user exists in database
      Travelstory travelstory = getTravelstoryOrThrow(travelstoryId);
      User user = getUserOrThrow(userId);

      // get some metadata from file if any
      Map<String, String> metadata = getMetadata(file);

      // create paths for file structuring
      String path = String.format("%s/%s/travelstories/%s", awsBucketName, user.getId(), travelstory.getId());
      String filename = String.format("%s", file.getOriginalFilename());


      try {

         // Upload image aws s3 server
         awsImageStoreService.save(path, filename, Optional.of(metadata), file.getInputStream());

         // Update user profile image
         travelstory.setImageUrl(filename);
         travelstoryRepository.save(travelstory);

      } catch (IOException exception) {
         throw new IllegalStateException(exception);
      }
   }


   public byte[] downloadTravelstoryImages(Long userId, Long travelstoryId) {
      // user, travelstory exists
      User user = getUserOrThrow(userId);
      Travelstory travelstory = getTravelstoryOrThrow(travelstoryId);

      // create download link
      String path = String.format("%s/%s/travelstories/%s", awsBucketName, user.getId(), travelstory.getId());

      return awsImageStoreService.download(path, travelstory.getImageUrl());
   }

   /*
    * PRIVATE METHODES
    * */

   private Map<String, String> getMetadata(MultipartFile file) {
      Map<String, String> metadata = new HashMap<>();
      metadata.put("Content-Type", file.getContentType());
      metadata.put("File-Size", String.valueOf(file.getSize()));
      return metadata;
   }

   private User getUserOrThrow(Long userId) {
      return userRepository
              .findById(userId)
              .stream()
              .filter(user -> user.getId().equals(userId))
              .findFirst()
              .orElseThrow(() -> new BadRequestException(String.format("User with id %s not found!", userId)));
   }


   private Travelstory getTravelstoryOrThrow(Long travelstoryId) {
      return travelstoryRepository
              .findById(travelstoryId)
              .stream()
              .filter(story -> story.getId().equals(travelstoryId))
              .findFirst()
              .orElseThrow(() -> new BadRequestException(String.format("Travelstory with id %s not found!", travelstoryId)));
   }

   private void isImageOrThrow(MultipartFile file) {
      if (!Arrays.asList(IMAGE_JPEG.getMimeType(), IMAGE_PNG.getMimeType())
              .contains(file.getContentType())) {
         throw new BadRequestException("File must be a image!");
      }
   }

   private void isFileEmptyOrThrow(MultipartFile file) {
      if (file.isEmpty()) {
         throw new BadRequestException(String.format("Cannot upload a empty file [ %s ]", file.getSize()));
      }
   }
}
