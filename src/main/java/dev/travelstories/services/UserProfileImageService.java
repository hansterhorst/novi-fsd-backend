package dev.travelstories.services;

import dev.travelstories.entities.User;
import dev.travelstories.exceptions.BadRequestException;
import dev.travelstories.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.IMAGE_JPEG;
import static org.apache.http.entity.ContentType.IMAGE_PNG;

@Service
public class UserProfileImageService {

   @Value("${aws-bucket-name}")
   private String awsBucketName;

   private final AwsImageStoreService awsImageStoreService;
   private final UserRepository userRepository;


   @Autowired
   public UserProfileImageService(AwsImageStoreService awsImageStoreService, UserRepository userRepository) {
      this.awsImageStoreService = awsImageStoreService;
      this.userRepository = userRepository;
   }


   public void uploadUserProfileImage(Long userId, MultipartFile file) {

      isFileEmptyOrThrow(file);

      isImageOrThrow(file);

      User user = getUserOrThrow(userId);

      Map<String, String> metadata = getMetadata(file);

      String path = String.format("%s/%s/profile", awsBucketName, user.getId());
      String filename = String.format("%s", file.getOriginalFilename());


      try {

         awsImageStoreService.save(path, filename, Optional.of(metadata), file.getInputStream());

         user.setProfileImage(filename);
         userRepository.save(user);

      } catch (IOException exception) {
         throw new IllegalStateException(exception);
      }
   }


   public byte[] downloadUserProfileImage(Long userId) {

      User user = getUserOrThrow(userId);

      String path = String.format("%s/%s/profile", awsBucketName, user.getId());

      return awsImageStoreService.download(path, user.getProfileImage());
   }


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
              .orElseThrow(() -> new BadRequestException(String.format("User profile with id %s not found!", userId)));
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
