package dev.travelstories.controllers;

import dev.travelstories.entities.Travelstory;
import dev.travelstories.entities.User;
import dev.travelstories.repositories.LikeRepository;
import dev.travelstories.repositories.TravelstoryRepository;
import dev.travelstories.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc()
@ActiveProfiles("test")
class LikeControllerTest {

   @Autowired
   private MockMvc mockMvc;

   @Autowired
   private TravelstoryRepository travelstoryRepository;

   @Autowired
   private UserRepository userRepository;

   @Autowired
   private LikeRepository likeRepository;

   @BeforeEach
   void setup() {
      userRepository.deleteAll();
      travelstoryRepository.deleteAll();
      likeRepository.deleteAll();
   }


   @Test
   @DisplayName("positive result - create a like a travelstory")
   @DirtiesContext
   void createLike() throws Exception {

      // GIVEN
      Long likeUserId = 2L;
      Long travelstoryId = 1L;
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

      User user2 = new User(
              2L,
              "Klaas",
              "Klaas Janssen",
              "klaas@mail.com",
              "klaas@mail.com",
              "password",
              "https://www.profileImage.com/12",
              "Deventer",
              "Nederland",
              "Profiel informatie"

      );
      userRepository.save(user2);

      List<Travelstory> travelstoryList = new ArrayList<>();
      travelstoryList.add(new Travelstory(
              1L,
              "Travelstory nummer 1",
              "Hans ter Horst",
              "We kunnen weer fietsen. De corona regels zijn weer wat versoepeld zodat je weer een beetje normaal naar de camping kunt gaan.",
              new Date(),
              "Bikepacking",
              "Nederland",
              true,
              "https://www.travelstories.travel/image/001",
              user
      ));

      travelstoryList.add(new Travelstory(
              2L,
              "Travelstory nummer 2",
              "Anne Rozendal",
              "We kunnen weer fietsen. De corona regels zijn weer wat versoepeld zodat je weer een beetje normaal naar de camping kunt gaan.",
              new Date(),
              "Bikepacking",
              "Nederland",
              true,
              "https://www.travelstories.travel/image/001",
              user
      ));

      travelstoryRepository.saveAll(travelstoryList);


      // WHEN
      ResultActions response = mockMvc.perform(post("/api/v1/users/travelstories/{travelstoryId}/likes/user/{userId}", travelstoryId, likeUserId));


      // THEN
      response.andExpect(status().isCreated()).andDo(print());
   }


   @Test
   @DisplayName("positive result - get all likes from travelstory by travelstory id")
   @DirtiesContext
   void getAllTravelstoryLikes() throws Exception {

      // GIVEN
      Long travelstoryId = 1L;
      User authUser = new User(
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
      userRepository.save(authUser);

      User likeUser = new User(
              2L,
              "Anne",
              "Rozendal",
              "anne@mail.com",
              "anne@mail.com",
              "password",
              "https://www.profileImage.com/15",
              "Leiden",
              "Nederland",
              "Profiel informatie"
      );
      userRepository.save(likeUser);

      Travelstory travelstory = new Travelstory(
              1L,
              "Travelstory nummer 1",
              "Hans ter Horst",
              "We kunnen weer fietsen. De corona regels zijn weer wat versoepeld zodat je weer een beetje normaal naar de camping kunt gaan.",
              new Date(),
              "Bikepacking",
              "Nederland",
              true,
              "https://www.travelstories.travel/image/001",
              authUser
      );
      travelstoryRepository.save(travelstory);

      mockMvc.perform(post("/api/v1/users/travelstories/{travelstoryId}/likes/user/{userId}", travelstory.getId(), likeUser.getId()));


      // WHEN
      ResultActions response = mockMvc.perform(get("/api/v1/users/travelstories/{travelstoryId}/likes", travelstoryId));


      // THEN
      response.andExpect(status().isOk()).andDo(print());
   }


   @Test
   @DisplayName("positive result - delete travelstory like by user id")
   @DirtiesContext
   void deleteLikeByUserId() throws Exception {

      // GIVEN
      Long travelstoryId = 1L;
      Long userId = 2L;
      User authUser = new User(
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
      userRepository.save(authUser);

      User user = new User(
              2L,
              "Anne",
              "Rozendal",
              "anne@mail.com",
              "anne@mail.com",
              "password",
              "https://www.profileImage.com/15",
              "Leiden",
              "Nederland",
              "Profiel informatie"
      );
      userRepository.save(user);

      Travelstory travelstory = new Travelstory(
              1L,
              "Travelstory nummer 1",
              "Hans ter Horst",
              "We kunnen weer fietsen. De corona regels zijn weer wat versoepeld zodat je weer een beetje normaal naar de camping kunt gaan.",
              new Date(),
              "Bikepacking",
              "Nederland",
              true,
              "https://www.travelstories.travel/image/001",
              authUser
      );
      travelstoryRepository.save(travelstory);

      mockMvc.perform(post("/api/v1/users/travelstories/{travelstoryId}/likes/user/{userId}", travelstory.getId(), user.getId()));


      // WHEN
      ResultActions response = mockMvc.perform(delete("/api/v1/users/travelstories/{travelstoryId}/likes/user/{userId}", travelstoryId, userId));


      // THEN
      response.andExpect(status().isOk()).andDo(print());

   }
}