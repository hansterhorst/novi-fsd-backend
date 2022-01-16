package dev.travelstories.dtos;

import dev.travelstories.entities.User;

public class RegisterDTO {


   private String firstname;
   private String lastname;
   private String username;
   private String email;
   private String password;

   public RegisterDTO() {
   }

   public RegisterDTO(String firstname, String lastname, String name, String username, String email, String password) {
      this.firstname = firstname;
      this.lastname = lastname;
      this.username = username;
      this.email = email;
      this.password = password;
   }

   public static User dtoToEntity(RegisterDTO registerDTO) {

      User user = new User();
      user.setFirstname(registerDTO.getFirstname());
      user.setLastname(registerDTO.getLastname());
      user.setUsername(registerDTO.getEmail());
      user.setEmail(registerDTO.getEmail());
      user.setPassword(registerDTO.getPassword());

      return user;
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

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

}
