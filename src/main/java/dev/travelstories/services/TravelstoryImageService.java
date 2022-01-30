package dev.travelstories.services;

import dev.travelstories.entities.Travelstory;
import dev.travelstories.entities.User;
import dev.travelstories.exceptions.BadRequestException;
import dev.travelstories.exceptions.RecordNotFoundException;
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


   public void uploadTravelstoryImages(Long userId, Long travelstoryId, MultipartFile file) {

      isFileEmptyOrThrow(file);

      isImageOrThrow(file);

      Travelstory travelstory = getTravelstoryOrThrow(travelstoryId);
      User user = getUserOrThrow(userId);

      Map<String, String> metadata = getMetadata(file);

      String path = String.format("%s/%s/travelstories/%s", awsBucketName, user.getId(), travelstory.getId());
      String filename = String.format("%s", file.getOriginalFilename());


      try {

         awsImageStoreService.save(path, filename, Optional.of(metadata), file.getInputStream());

         // Update user profile image
         travelstory.setImageUrl(file.getOriginalFilename());
         travelstoryRepository.save(travelstory);

      } catch (IOException exception) {
         throw new IllegalStateException(exception);
      }
   }


   public byte[] downloadTravelstoryImages(Long userId, Long travelstoryId) {

      User user = getUserOrThrow(userId);
      Travelstory travelstory = getTravelstoryOrThrow(travelstoryId);

      String path = String.format("%s/%s/travelstories/%s", awsBucketName, user.getId(), travelstory.getId());

      return awsImageStoreService.download(path, travelstory.getImageUrl());
   }


   private Map<String, String> getMetadata(MultipartFile file) {
      Map<String, String> metadata = new HashMap<>();
      metadata.put("Content-Type", file.getContentType());
      metadata.put("File-Size", String.valueOf(file.getSize()));
      return metadata;
   }

   private User getUserOrThrow(Long userId) {
      return userRepository.findById(userId).orElseThrow(() ->
              new RecordNotFoundException(String.format("User with id %s not found!", userId)));
   }


   private Travelstory getTravelstoryOrThrow(Long travelstoryId) {
      return travelstoryRepository.findById(travelstoryId).orElseThrow(() ->
              new RecordNotFoundException(String.format("Travelstory with id %s not found!", travelstoryId)));
   }

   private void isImageOrThrow(MultipartFile file) {
      if (!Arrays.asList(IMAGE_JPEG.getMimeType(), IMAGE_PNG.getMimeType())
              .contains(file.getContentType())) {
         throw new BadRequestException("Foto moet een .jpg of .png file zijn.");
      }
   }

   private void isFileEmptyOrThrow(MultipartFile file) {
      if (file.isEmpty()) {
         throw new BadRequestException(String.format("File bevat geen foto [ %s ].", file.getSize()));
      }
   }
}
