package dev.travelstories.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.travelstories.entities.User;
import dev.travelstories.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc()
@ActiveProfiles("test")
class UserControllerTest {

   @Autowired
   private MockMvc mockMvc;

   @Autowired
   private UserRepository userRepository;

   @Autowired
   private ObjectMapper objectMapper;

   @BeforeEach
   void setup() {
      userRepository.deleteAll();
   }


   @Test
   @DisplayName("positive result - create a new user")
   void createNewUser() throws Exception {

      User user = new User(
              1L,
              "Hans",
              "ter Horst",
              "hans@mail.com",
              "hans@mail.com",
              "password",
              "https://www.profileImage.com/12",
              "Delden",
              "Nederland",
              "Profiel informatie"

      );


      ResultActions response = mockMvc.perform(post("/api/v1/users")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(user)));

      response.andExpect(status().isCreated());
   }


   @Test
   @DisplayName("positive result - get all user")
   public void getAllUsers() throws Exception {

      // GIVEN
      List<User> userList = new ArrayList<>();
      userList.add(new User(
              2L,
              "Hans",
              "ter Horst",
              "hans@mail.com",
              "hans@mail.com",
              "password",
              "https://www.profileImage.com/12",
              "Delden",
              "Nederland",
              "Profiel informatie"
      ));
      userList.add(new User(
              3L,
              "Klaas",
              "janssen",
              "klaas@mail.com",
              "klaas@mail.com",
              "password",
              "https://www.profileImage.com/15",
              "Deventer",
              "Nederland",
              "Profiel informatie"
      ));
      userRepository.saveAll(userList);


      // WHEN
      ResultActions response = mockMvc.perform(get("/api/v1/users")
      );


      // THEN
      response.andExpect(status().isOk());
   }


   @Test
   @DisplayName("negative result - get user by id")
   public void getUserById() throws Exception {

      // GIVEN
      Long userId = 1L;
      User user = new User(
              4L,
              "Hans",
              "ter Horst",
              "hans@mail.com",
              "hans@mail.com",
              "password",
              "https://www.profileImage.com/12",
              "Delden",
              "Nederland",
              "Profiel informatie"
      );
      userRepository.save(user);


      // WHEN
      ResultActions response = mockMvc.perform(get("/api/v1/users/user/{userId}", userId));


      // THEN
      response.andExpect(status().isNotFound());
   }


   @Test
   @DisplayName("positive result - get user by email")
   void getUserByEmail() throws Exception {

      // GIVEN
      String email = "hans@mail.com";
      User user = new User(
              5L,
              "Hans",
              "ter Horst",
              "hans@mail.com",
              "hans@mail.com",
              "password",
              "https://www.profileImage.com/12",
              "Delden",
              "Nederland",
              "Profiel informatie"
      );
      userRepository.save(user);


      // WHEN
      ResultActions response = mockMvc.perform(get("/api/v1/users/user")
              .param("email", email));


      // THEN
      response.andExpect(status().isOk());
   }


   @Test
   @DisplayName("negative result - update user")
   void updateUserById() throws Exception {

      userRepository.deleteAll();

      // GIVEN
      Long userId = 6L;
      User user = new User(
              6L,
              "Hans",
              "ter Horst",
              "hans@mail.com",
              "hans@mail.com",
              "password",
              "https://www.profileImage.com/12",
              "Delden",
              "Nederland",
              "Profiel informatie"
      );
      userRepository.save(user);

      User updateUser = new User(
              6L,
              "Klaas",
              "Janssen",
              "klaas@mail.com",
              "klaas@mail.com",
              "password",
              "https://www.profileImage.com/38",
              "Deventer",
              "Nederland",
              "Profiel informatie"
      );


      // WHEN
      ResultActions response = mockMvc.perform(put("/api/v1/users/user/{userId}", userId)
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(updateUser)));


      // THEN
      response.andExpect(status().isNotFound());

   }


   @Test
   @DisplayName("positive result - delete user")
   void deleteUserById() throws Exception {

      userRepository.deleteAll();

      // GIVEN
      Long userId = 7L;
      User user = new User(
              7L,
              "Hans",
              "ter Horst",
              "hans@mail.com",
              "hans@mail.com",
              "password",
              "https://www.profileImage.com/12",
              "Delden",
              "Nederland",
              "Profiel informatie"
      );
      userRepository.save(user);


      // WHEN
      ResultActions response = mockMvc.perform(delete("/api/v1/users/user/{userId}", userId));


      // THEN
      response.andExpect(status().isOk());
   }
}