package dev.travelstories.dtos;

import dev.travelstories.entities.Travelstory;
import dev.travelstories.entities.User;

import java.util.List;


public class UserDTO {

   private Long id;
   private String firstname;
   private String lastname;
   private String email;
   private String username;
   private String password;
   private List<Travelstory> travelstories;



   /*
    * CONSTRUCTORS
    * */

   public UserDTO() {
   }

   public UserDTO(Long id, String firstname, String lastname, String email, String username, String password, List<Travelstory> travelstories) {
      this.id = id;
      this.firstname = firstname;
      this.lastname = lastname;
      this.email = email;
      this.username = username;
      this.password = password;
      this.travelstories = travelstories;
   }


   /*
    * METHODS
    * */

   public static dev.travelstories.dtos.UserDTO entityToDTO(User user) {

      dev.travelstories.dtos.UserDTO userDTO = new dev.travelstories.dtos.UserDTO();
      userDTO.setId(user.getId());
      userDTO.setFirstname(user.getFirstname());
      userDTO.setLastname(user.getLastname());
      userDTO.setEmail(user.getEmail());
      userDTO.setUsername(user.getUsername());
      userDTO.setPassword(user.getPassword());
      userDTO.setTravelstories(user.getTravelstories());

      return userDTO;
   }


   public static User dtoToEntity(dev.travelstories.dtos.UserDTO userDTO) {

      User user = new User();
      user.setId(userDTO.getId());
      user.setFirstname(userDTO.getFirstname());
      user.setLastname(userDTO.getLastname());
      user.setEmail(userDTO.getEmail());
      user.setUsername(userDTO.getUsername());
      user.setPassword(userDTO.getPassword());
      user.setTravelstories(userDTO.getTravelstories());

      return user;
   }


   /*
    * GETTERS & SETTERS
    * */

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getFirstname() {
      return firstname;
   }

   public void setFirstname(String firstname) {
      this.firstname = firstname;
   }

   public String getLastname() {
      return lastname;
   }

   public void setLastname(String lastname) {
      this.lastname = lastname;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public List<Travelstory> getTravelstories() {
      return travelstories;
   }

   public void setTravelstories(List<Travelstory> travelstories) {
      this.travelstories = travelstories;
   }
}



