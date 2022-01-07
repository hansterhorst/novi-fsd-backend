package dev.travelstories.controllers;

import dev.travelstories.dtos.UserDTO;
import dev.travelstories.entities.User;
import dev.travelstories.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/users")
@CrossOrigin(value = "http://localhost:3000")
public class UserController {


   private final UserService userService;

   public UserController(UserService userService) {
      this.userService = userService;
   }


   /*
    * CRUD OPERATIONS
    * */

   //   CREATE new user
   @PostMapping()
   public ResponseEntity<String> createNewUser(@RequestBody UserDTO userDTO) {

      User user = UserDTO.dtoToEntity(userDTO);

      userService.createNewUser(user);

      return new ResponseEntity<>(String.format("User: %s %s created successfully", user.getFirstname(), user.getLastname()), HttpStatus.CREATED);
   }


   //   GET all users
   @GetMapping()
   public ResponseEntity<List<UserDTO>> getAllUsers() {

      List<User> userList = userService.getAllUsers();
      List<UserDTO> userDTOList = userList.stream().map(UserDTO::entityToDTO).collect(Collectors.toList());

      return new ResponseEntity<>(userDTOList, HttpStatus.OK);
   }


   //   GET user by id
   @GetMapping(path = "/{userId}")
   public ResponseEntity<UserDTO> getUserById(@PathVariable(value = "userId") Long userId) {

      User user = userService.getUserById(userId);

      return new ResponseEntity<>(UserDTO.entityToDTO(user), HttpStatus.OK);
   }


   //   GET user by email
   @GetMapping(path = "/user")
   public ResponseEntity<UserDTO> getUserById(@RequestParam(name = "email") String email) {

      User user = userService.getUserByEmail(email);

      return new ResponseEntity<>(UserDTO.entityToDTO(user), HttpStatus.OK);
   }


   //   UPDATE user by id
   @PutMapping(path = "/{userId}")
   public ResponseEntity<String> updateUserById(@PathVariable(value = "userId") Long userId,
                                                @RequestBody UserDTO userDTO) {

      User user = UserDTO.dtoToEntity(userDTO);
      userService.updateUserById(userId, user);

      return new ResponseEntity<>(String.format("User: %s %s is successfully updated", user.getFirstname(), user.getLastname()), HttpStatus.OK);
   }


   //   DELETE user by id
   @DeleteMapping(path = "{userId}")
   public ResponseEntity<String> deleteUserById(@PathVariable(value = "userId") Long userId) {

      userService.deleteUserById(userId);

      return new ResponseEntity<>("User is successfully deleted", HttpStatus.OK);
   }

}
