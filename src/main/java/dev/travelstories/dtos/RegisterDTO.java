package dev.travelstories.dtos;

import dev.travelstories.entities.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RegisterDTO {


   @NotBlank(message = "Voornaam is verplicht")
   private String firstname;

   @NotBlank(message = "Achternaam is verplicht")
   private String lastname;

   @NotBlank(message = "Email is verplicht")
   @Email(message = "Email is niet geldig")
   private String email;

   @NotBlank(message = "Wachtwoord is verplicht")
   @Size(min = 6, message = "Wachtwoord minimaal 6 characters lang")
   private String password;


   public RegisterDTO() {
   }

   public RegisterDTO(String firstname, String lastname, String email, String password) {
      this.firstname = firstname;
      this.lastname = lastname;
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
