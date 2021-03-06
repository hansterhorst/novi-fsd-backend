package dev.travelstories.controllers;

import dev.travelstories.dtos.UserDTO;
import dev.travelstories.entities.User;
import dev.travelstories.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static dev.travelstories.constants.Constants.AUTHORITY_ADMIN_ACCESS_URL;
import static dev.travelstories.constants.Constants.AUTHORITY_USER_ACCESS_URL;

@RestController
@CrossOrigin(value = "http://localhost:3000")
public class UserController {


   private final UserService userService;

   public UserController(UserService userService) {
      this.userService = userService;
   }


   //   CREATE new user
   @PostMapping(path = AUTHORITY_USER_ACCESS_URL)
   public ResponseEntity<String> createNewUser(@RequestBody UserDTO userDTO) {

      User user = UserDTO.dtoToEntity(userDTO);

      userService.createNewUser(user);

      return new ResponseEntity<>(String.format("User: %s %s created successfully", user.getFirstname(), user.getLastname()), HttpStatus.CREATED);
   }


   //   GET all users
   @GetMapping(path = AUTHORITY_USER_ACCESS_URL)
   public ResponseEntity<List<UserDTO>> getAllUsers() {

      List<User> userList = userService.getAllUsers();
      List<UserDTO> userDTOList = userList.stream().map(UserDTO::entityToDTO).collect(Collectors.toList());

      return new ResponseEntity<>(userDTOList, HttpStatus.OK);
   }


   //   GET user by id
   @GetMapping(path = AUTHORITY_USER_ACCESS_URL + "/user/{userId}")
   public ResponseEntity<UserDTO> getUserById(@PathVariable(value = "userId") Long userId) {

      User user = userService.getUserById(userId);

      return new ResponseEntity<>(UserDTO.entityToDTO(user), HttpStatus.OK);
   }


   //   GET user by email
   @GetMapping(path = AUTHORITY_USER_ACCESS_URL + "/user")
   public ResponseEntity<UserDTO> getUserByEmail(@RequestParam(name = "email") String email) {

      User user = userService.getUserByEmail(email);

      return new ResponseEntity<>(UserDTO.entityToDTO(user), HttpStatus.OK);
   }


   //   UPDATE user by id
   @PutMapping(path = AUTHORITY_USER_ACCESS_URL + "/user/{userId}")
   public ResponseEntity<String> updateUserById(@PathVariable(value = "userId") Long userId,
                                                @Valid @RequestBody UserDTO userDTO) {

      User user = UserDTO.dtoToEntity(userDTO);
      userService.updateUserById(userId, user);

      return new ResponseEntity<>(String.format("User: %s %s is successfully updated", user.getFirstname(), user.getLastname()), HttpStatus.OK);
   }


   //   DELETE user by id
   @DeleteMapping(path = AUTHORITY_ADMIN_ACCESS_URL + "/user/{userId}")
   public ResponseEntity<String> deleteUserById(@PathVariable(value = "userId") Long userId) {

      userService.deleteUserById(userId);

      return new ResponseEntity<>("User is successfully deleted", HttpStatus.OK);
   }

}
