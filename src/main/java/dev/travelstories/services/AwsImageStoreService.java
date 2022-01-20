package dev.travelstories.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import dev.travelstories.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@Service
public class AwsImageStoreService {

   private final AmazonS3 s3;

   @Autowired
   public AwsImageStoreService(AmazonS3 s3) {
      this.s3 = s3;
   }


   public void save(String path, String filename, Optional<Map<String, String>> optionalMetadata, InputStream inputStream) {

      ObjectMetadata metadata = new ObjectMetadata();

      optionalMetadata.ifPresent(map -> {
         if (!map.isEmpty()) {
            map.forEach(metadata::addUserMetadata);
         }
      });

      try {
         s3.putObject(path, filename, inputStream, metadata);
      } catch (AmazonServiceException exception) {
         throw new BadRequestException("Failed to store file to S3");
      }
   }


   public byte[] download(String path, String key) {

      try {
         S3Object object = s3.getObject(path, key);
         return IOUtils.toByteArray(object.getObjectContent());

      } catch (AmazonServiceException | IOException exception) {
         throw new BadRequestException("Failed to download image from server");
      }
   }
}
