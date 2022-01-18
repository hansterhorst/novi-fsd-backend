package dev.travelstories.dtos;

import dev.travelstories.entities.Follow;
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
   private String profileImage;
   private String city;
   private String country;
   private String bio;
   private List<Travelstory> travelstories;
   private List<Follow> follows;



   /*
    * CONSTRUCTORS
    * */

   public UserDTO() {
   }

   public UserDTO(Long id, String firstname, String lastname, String email, String username, String password, String profileImage, List<Travelstory> travelstories, List<Follow> follows, String city, String country, String bio) {
      this.id = id;
      this.firstname = firstname;
      this.lastname = lastname;
      this.email = email;
      this.username = username;
      this.password = password;
      this.profileImage = profileImage;
      this.travelstories = travelstories;
      this.follows = follows;
      this.city = city;
      this.country = country;
      this.bio = bio;
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
//      userDTO.setPassword(user.getPassword());
      userDTO.setProfileImage(user.getProfileImage());
      userDTO.setTravelstories(user.getTravelstories());
      userDTO.setFollows(user.getFollows());
      userDTO.setCity(user.getCity());
      userDTO.setCountry(user.getCountry());
      userDTO.setBio(user.getBio());

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
      user.setProfileImage(userDTO.getProfileImage());
      user.setTravelstories(userDTO.getTravelstories());
      user.setCity(userDTO.getCity());
      user.setCountry(userDTO.getCountry());
      user.setBio(userDTO.getBio());

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

   public String getProfileImage() {
      return profileImage;
   }

   public void setProfileImage(String profileImage) {
      this.profileImage = profileImage;
   }

   public List<Follow> getFollows() {
      return follows;
   }

   public void setFollows(List<Follow> follows) {
      this.follows = follows;
   }

   public String getCity() {
      return city;
   }

   public void setCity(String city) {
      this.city = city;
   }

   public String getCountry() {
      return country;
   }

   public void setCountry(String country) {
      this.country = country;
   }

   public String getBio() {
      return bio;
   }

   public void setBio(String bio) {
      this.bio = bio;
   }
}



