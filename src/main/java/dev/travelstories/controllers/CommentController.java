package dev.travelstories.controllers;


import dev.travelstories.dtos.CommentDTO;
import dev.travelstories.entities.Comment;
import dev.travelstories.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static dev.travelstories.constants.Constants.*;


@RestController
@RequestMapping(path = AUTHORITY_ACCESS_URL)
@CrossOrigin(value = "http://localhost:3000")
public class CommentController {


   private final CommentService commentService;

   /*
    * CONSTRUCTORS
    * */

   @Autowired
   public CommentController(CommentService commentService) {
      this.commentService = commentService;
   }


   /*
    * CRUD OPERATIONS
    * */

   //   CREATE a new comment on a travelstory by travelstoryId
   @PostMapping(path = "/travelstories/{travelstoryId}/comments/user/{userId}")
   public ResponseEntity<String> createComment(@PathVariable(value = "travelstoryId") Long travelstoryId,
                                               @PathVariable(value = "userId") Long userId,
                                               @RequestBody CommentDTO commentDTO) {

      Comment comment = CommentDTO.dtoToEntity(commentDTO);
      commentService.createComment(travelstoryId, userId, comment);

      return new ResponseEntity<>("Comment is successfully created", HttpStatus.CREATED);
   }


   //   GET all comments from travelstory by travelstoryId
   @GetMapping(path = "/travelstories/{travelstoryId}/comments")
   public ResponseEntity<List<CommentDTO>> getCommentsFromTravelstoryById(@PathVariable(value = "travelstoryId") Long travelstoryId) {

      List<CommentDTO> commentDTOList = commentService.getCommentsFromTravelstoryById(travelstoryId);

      return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
   }


   //   GET a comment from a travelstory by commentId and travelstoryId
   @GetMapping(path = "/travelstories/{travelstoryId}/comments/{commentId}")
   public ResponseEntity<CommentDTO> getCommentFromTravelstoryById(@PathVariable(value = "travelstoryId") Long travelstoryId,
                                                                   @PathVariable(value = "commentId") Long commentId) {

      Comment comment = commentService.getCommentFromTravelstoryById(travelstoryId, commentId);

      return new ResponseEntity<>(CommentDTO.entityToDTO(comment), HttpStatus.OK);
   }


   //   UPDATE a comment by commentId on a travelstory by travelstoryId
   @PutMapping(path = "/travelstories/{travelstoryId}/comments/{commentId}")
   public ResponseEntity<CommentDTO> updateCommitById(@PathVariable(value = "travelstoryId") Long travelstoryId,
                                                      @PathVariable(value = "commentId") Long commentId,
                                                      @RequestBody CommentDTO commentDTO) {

      Comment comment = CommentDTO.dtoToEntity(commentDTO);

      Comment updatedComment = commentService.updateCommitById(travelstoryId, commentId, comment);

      return new ResponseEntity<>(CommentDTO.entityToDTO(updatedComment), HttpStatus.OK);
   }


   //   DELETE a comment by commentId on a travelstory by travelstoryId
   @DeleteMapping(path = "/travelstories/{travelstoryId}/comments/{commentId}")
   public ResponseEntity<String> deleteCommitById(@PathVariable(value = "travelstoryId") Long travelstoryId,
                                                  @PathVariable(value = "commentId") Long commentId) {

      return new ResponseEntity<>(commentService.deleteCommitById(travelstoryId, commentId), HttpStatus.OK);
   }

}
