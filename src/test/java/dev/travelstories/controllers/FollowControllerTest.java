package dev.travelstories.controllers;

import dev.travelstories.entities.User;
import dev.travelstories.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc()
@ActiveProfiles("test")
class FollowControllerTest {

   @Autowired
   private MockMvc mockMvc;

   @Autowired
   private UserRepository userRepository;

   @BeforeEach
   void setup() {
      userRepository.deleteAll();
   }


   @Test
   @DisplayName("positive result - create a follow user by id")
   void createFollowUserByUserId() throws Exception {

      // GIVEN
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
      userRepository.save(user);

      User authUser = new User(
              2L,
              "Klaas",
              "janssen",
              "klaas@mail.com",
              "klaas@mail.com",
              "password",
              "https://www.profileImage.com/20",
              "Deventer",
              "Nederland",
              "Profiel informatie"

      );
      userRepository.save(authUser);


      // WHEN
      ResultActions response = mockMvc.perform(post("/api/v1/users/{userId}/follow/{authUserId}",
              user.getId(), authUser.getId()));


      // THEN
      response.andExpect(status().isCreated())
              .andDo(print());
   }


   @Test
   @DisplayName("positive result - get all follow users ")
   void getAllFollowUsers() throws Exception {

      // GIVEN
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
      userRepository.save(user);

      User authUser = new User(
              2L,
              "Klaas",
              "janssen",
              "klaas@mail.com",
              "klaas@mail.com",
              "password",
              "https://www.profileImage.com/20",
              "Deventer",
              "Nederland",
              "Profiel informatie"

      );
      userRepository.save(authUser);

      mockMvc.perform(post("/api/v1/users/{userId}/follow/{authUserId}",
              user.getId(), authUser.getId()));


      // WHEN
      ResultActions response = mockMvc.perform(get("/api/v1/users/{userId}/follow",
              user.getId()));


      // THEN
      response.andExpect(status().isOk())
              .andDo(print());
   }


   @Test
   @DisplayName("positive result - delete a follow user ")
   void deleteFollowUserById() throws Exception {

      // GIVEN
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
      userRepository.save(user);

      User authUser = new User(
              2L,
              "Klaas",
              "janssen",
              "klaas@mail.com",
              "klaas@mail.com",
              "password",
              "https://www.profileImage.com/20",
              "Deventer",
              "Nederland",
              "Profiel informatie"

      );
      userRepository.save(authUser);

      mockMvc.perform(post("/api/v1/users/{userId}/follow/{authUserId}",
              user.getId(), authUser.getId()));


      // WHEN
      ResultActions response = mockMvc.perform(delete("/api/v1/users/{userId}/follow/{authUserId}",
              user.getId(), authUser.getId()));


      // THEN
      response.andExpect(status().isOk())
              .andDo(print());
   }
}