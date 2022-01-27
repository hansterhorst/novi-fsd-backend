package dev.travelstories.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.travelstories.entities.Travelstory;
import dev.travelstories.entities.User;
import dev.travelstories.repositories.TravelstoryRepository;
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
import java.util.Date;
import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc()
@ActiveProfiles("test")
class TravelstoryControllerTest {

   @Autowired
   private MockMvc mockMvc;

   @Autowired
   private TravelstoryRepository travelstoryRepository;

   @Autowired
   private UserRepository userRepository;

   @Autowired
   private ObjectMapper objectMapper;

   @BeforeEach
   void setup() {
      userRepository.deleteAll();
      travelstoryRepository.deleteAll();
   }


   @Test
   @DisplayName("positive result - create a new travelstory")
   void createTravelstory() throws Exception {

      travelstoryRepository.deleteAll();
      userRepository.deleteAll();

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
              user
      );


      // WHEN
      ResultActions response = mockMvc.perform(post("/api/v1/users/travelstories/user/{userId}", user.getId())
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(travelstory)));


      // THEN
      response.andExpect(status().isCreated());
   }


   @Test
   @DisplayName("positive result - get all travelstories")
   void getAllTravelstories() throws Exception {

      travelstoryRepository.deleteAll();
      userRepository.deleteAll();

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
              "Klaas Janssen",
              "We kunnen weer fietsen. De corona regels zijn weer wat versoepeld zodat je weer een beetje normaal naar de camping kunt gaan.",
              new Date(),
              "Stedentrip",
              "Nederland",
              true,
              "https://www.travelstories.travel/image/002",
              user
      ));
      travelstoryRepository.saveAll(travelstoryList);


      // WHEN
      ResultActions response = mockMvc.perform(get("/api/v1/users/travelstories/"));


      // THEN
      response.andExpect(status().isOk());

   }


   @Test
   @DisplayName("positive result - get all travelstory by user id")
   void getAllTravelstoriesByUserId() throws Exception {

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
              "Klaas Janssen",
              "We kunnen weer fietsen. De corona regels zijn weer wat versoepeld zodat je weer een beetje normaal naar de camping kunt gaan.",
              new Date(),
              "Stedentrip",
              "Nederland",
              true,
              "https://www.travelstories.travel/image/002",
              user
      ));
      travelstoryRepository.saveAll(travelstoryList);


      // WHEN
      ResultActions response = mockMvc.perform(get("/api/v1/users/travelstories/user/{userId}", user.getId()));


      // THEN
      response.andExpect(status().isOk());
   }


   @Test
   @DisplayName("positive result - get a travelstory by id")
   void getTravelstoryById() throws Exception {

      travelstoryRepository.deleteAll();
      userRepository.deleteAll();

      // GIVEN
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
              "Klaas Janssen",
              "We kunnen weer fietsen. De corona regels zijn weer wat versoepeld zodat je weer een beetje normaal naar de camping kunt gaan.",
              new Date(),
              "Stedentrip",
              "Nederland",
              true,
              "https://www.travelstories.travel/image/002",
              user
      ));
      travelstoryRepository.saveAll(travelstoryList);


      // WHEN
      ResultActions response = mockMvc.perform(get("/api/v1/users/travelstories/{travelstoryId}", travelstoryId));


      // THEN
      response.andExpect(status().isOk());
   }


   @Test
   @DisplayName("positive result - update a travelstory by id")
   void updateTravelstoryById() throws Exception {

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
              user
      );
      travelstoryRepository.save(travelstory);

      Travelstory updateTravelstory = new Travelstory(
              1L,
              "Travelstory Update",
              "Hans ter Horst",
              "We kunnen weer fietsen. De corona regels zijn weer wat versoepeld zodat je weer een beetje normaal naar de camping kunt gaan.",
              new Date(),
              "Bikepacking",
              "Nederland",
              true,
              "https://www.travelstories.travel/image/002",
              user
      );


      // WHEN
      ResultActions response = mockMvc.perform(put("/api/v1/users/travelstories/{travelstoryId}", travelstory.getId())
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(updateTravelstory)));


      // THEN
      response.andExpect(status().isOk());
   }


   @Test
   @DisplayName("positive result - delete a travelstory by id")
   void deleteTravelstoryById() throws Exception {

      travelstoryRepository.deleteAll();
      userRepository.deleteAll();

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
              user
      );
      travelstoryRepository.save(travelstory);


      // WHEN
      ResultActions response = mockMvc.perform(delete("/api/v1/users/travelstories/{travelstoryId}", travelstory.getId()));


      // THEN
      response.andExpect(status().isOk());
   }


   @Test
   @DisplayName("positive result - get all public travelstories")
   void getAllPublicTravelstories() throws Exception {

      travelstoryRepository.deleteAll();
      userRepository.deleteAll();

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
              "Klaas Janssen",
              "We kunnen weer fietsen. De corona regels zijn weer wat versoepeld zodat je weer een beetje normaal naar de camping kunt gaan.",
              new Date(),
              "Stedentrip",
              "Nederland",
              true,
              "https://www.travelstories.travel/image/002",
              user
      ));
      travelstoryRepository.saveAll(travelstoryList);


      // WHEN
      ResultActions response = mockMvc.perform(get("/api/v1/public/travelstories/"));


      // THEN
      response.andExpect(status().isOk());
   }


   @Test
   @DisplayName("positive result - get a public travelstory by id")
   void getPublicTravelstoryById() throws Exception {

      travelstoryRepository.deleteAll();
      userRepository.deleteAll();

      // GIVEN
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
              "Klaas Janssen",
              "We kunnen weer fietsen. De corona regels zijn weer wat versoepeld zodat je weer een beetje normaal naar de camping kunt gaan.",
              new Date(),
              "Stedentrip",
              "Nederland",
              true,
              "https://www.travelstories.travel/image/002",
              user
      ));
      travelstoryRepository.saveAll(travelstoryList);


      // WHEN
      ResultActions response = mockMvc.perform(get("/api/v1/public/travelstories/{travelstoryId}", travelstoryId));


      // THEN
      response.andExpect(status().isOk());
   }
}