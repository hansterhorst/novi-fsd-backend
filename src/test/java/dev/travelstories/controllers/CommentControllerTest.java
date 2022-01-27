package dev.travelstories.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.travelstories.entities.Comment;
import dev.travelstories.entities.Travelstory;
import dev.travelstories.entities.User;
import dev.travelstories.repositories.CommentRepository;
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

import java.util.Date;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc()
@ActiveProfiles("test")
class CommentControllerTest {

   @Autowired
   private MockMvc mockMvc;

   @Autowired
   private CommentRepository commentRepository;

   @Autowired
   private TravelstoryRepository travelstoryRepository;

   @Autowired
   private UserRepository userRepository;

   @Autowired
   private ObjectMapper objectMapper;

   @BeforeEach
   void setup() {
      commentRepository.deleteAll();
      travelstoryRepository.deleteAll();
      userRepository.deleteAll();
   }

   @Test
   @DisplayName("positive result - create a user comment on a travelstory")
   void createComment() throws Exception {

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

      Comment comment = new Comment(
              1L,"Mooi verhaal.", travelstory, user.getId()
      );


      // WHEN
      ResultActions response = mockMvc.perform(post("/api/v1/users/travelstories/{travelstoryId}/comments/user/{userId}",
              travelstory.getId(), user.getId())
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(comment)));

      // THEN
      response.andExpect(status().isCreated());
   }


   @Test
   @DisplayName("positive result - get all comments from a travelstory")
   void getCommentsFromTravelstoryById() throws Exception {

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

      Comment comment = new Comment(
              1L,"Mooi verhaal.", travelstory, user.getId()
      );
      commentRepository.save(comment);

      // WHEN
      ResultActions response = mockMvc.perform(get("/api/v1/users/travelstories/{travelstoryId}/comments",
              travelstory.getId()));


      // THEN
      response.andExpect(status().isOk()).andDo(print());

   }

   @Test
   @DisplayName("positive result - get a comment from a travelstory")
   void getCommentFromTravelstoryById() throws Exception {

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

      Comment comment = new Comment(
              1L,"Mooi verhaal.", travelstory, user.getId()
      );
      commentRepository.save(comment);


      // WHEN
      ResultActions response = mockMvc.perform(get("/api/v1/users//travelstories/{travelstoryId}/comments/{commentId}",
              travelstory.getId(), comment.getId()));


      // THEN
      response.andExpect(status().isOk()).andDo(print());

   }

   @Test
   @DisplayName("positive result - update a comment by id")
   void updateCommitById() throws Exception {

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

      Comment comment = new Comment(
              1L,"Mooi verhaal.", travelstory, user.getId()
      );
      commentRepository.save(comment);

      Comment updateComment = new Comment(
              1L,"Heb mijn reactie veranderd.", travelstory, user.getId()
      );


      // WHEN
      ResultActions response = mockMvc.perform(put("/api/v1/users/travelstories/{travelstoryId}/comments/{commentId}",
              travelstory.getId(), comment.getId())
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(updateComment)));


      // THEN
      response.andExpect(status().isOk()).andDo(print());

   }

   @Test
   void deleteCommitById() throws Exception {

      commentRepository.deleteAll();
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

      Comment comment = new Comment(
              1L,"Mooi verhaal.", travelstory, user.getId()
      );
      commentRepository.save(comment);


      // WHEN
      ResultActions response = mockMvc.perform(delete("/api/v1/users/travelstories/{travelstoryId}/comments/{commentId}",
              travelstory.getId(), comment.getId()));


      // THEN
      response.andExpect(status().isOk()).andDo(print());
   }
}