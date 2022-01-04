package dev.travelstories.services;

import dev.travelstories.entities.User;
import dev.travelstories.exceptions.BadRequestException;
import dev.travelstories.exceptions.RecordNotFoundException;
import dev.travelstories.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


   private final UserRepository userRepository;

   @Autowired
   public UserService(UserRepository userRepository) {
      this.userRepository = userRepository;
   }


   /*
    * CRUD OPERATIONS
    * */

   //   CREATE new user
   public void createNewUser(User user) {

      if(userRepository.existsByEmail(user.getEmail())){
         throw new BadRequestException("User already exist");
      }

      user.setUsername(user.getEmail());

      userRepository.save(user);
   }


   //   GET all users
   public List<User> getAllUsers() {
      return userRepository.findAll();
   }


   //   GET user by userId
   public User getUserById(Long userId) {
      return userRepository.findById(userId).orElseThrow(() ->
         new RecordNotFoundException(String.format("User with id: %s not found.", userId)));
   }


   //   GET user by userId
   public User getUserByEmail(String email) {
      return userRepository.findByEmail(email).orElseThrow(() ->
         new RecordNotFoundException(String.format("User with email: %s not found.", email)));
   }


   //   UPDATE a user by userId
   public void updateUserById(Long userId, User user) {

      User updateUser = userRepository.findById(userId).orElseThrow(() ->
         new RecordNotFoundException(String.format("User with id: %s not found.", userId)));

      updateUser.setFirstname(user.getFirstname());
      updateUser.setLastname(user.getLastname());
      updateUser.setUsername(user.getUsername());
      updateUser.setEmail(user.getEmail());

      userRepository.save(updateUser);
   }


   //   DELETE user by userId
   public void deleteUserById(Long userId) {

      User deleteUser = userRepository.findById(userId).orElseThrow(() ->
         new RecordNotFoundException(String.format("User with id: %s not found.", userId)));

      userRepository.delete(deleteUser);
   }

}
