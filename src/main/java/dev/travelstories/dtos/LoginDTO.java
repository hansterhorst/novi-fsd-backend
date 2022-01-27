package dev.travelstories.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginDTO {

   @NotBlank(message = "Email nodig voor inloggen")
   @Email()
   private String usernameOrEmail;

   @NotBlank(message = "Wachtwoord nodig voor inloggen")
   @Size(min = 6, message = "Wachtwoord minimaal 6 characters lang")
   private String password;


   public LoginDTO() {
   }

   public LoginDTO(String usernameOrEmail, String password) {
      this.usernameOrEmail = usernameOrEmail;
      this.password = password;
   }


   public String getUsernameOrEmail() {
      return usernameOrEmail;
   }

   public void setUsernameOrEmail(String usernameOrEmail) {
      this.usernameOrEmail = usernameOrEmail;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

}
