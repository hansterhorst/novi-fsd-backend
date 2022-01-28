package dev.travelstories.services;

import dev.travelstories.dtos.CommentDTO;
import dev.travelstories.entities.Comment;
import dev.travelstories.entities.Travelstory;
import dev.travelstories.entities.User;
import dev.travelstories.exceptions.BadRequestException;
import dev.travelstories.exceptions.RecordNotFoundException;
import dev.travelstories.repositories.CommentRepository;
import dev.travelstories.repositories.TravelstoryRepository;
import dev.travelstories.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentService {

   private final CommentRepository commentRepository;
   private final UserRepository userRepository;
   private final TravelstoryRepository travelstoryRepository;


   @Autowired
   public CommentService(CommentRepository commentRepository, UserRepository userRepository, TravelstoryRepository travelstoryRepository) {
      this.commentRepository = commentRepository;
      this.userRepository = userRepository;
      this.travelstoryRepository = travelstoryRepository;
   }


   //   POST a new comment on a travelstory by travelId
   public void createComment(Long travelstoryId, Long userId, Comment comment) {

      User user = userRepository.findById(userId).orElseThrow(() ->
              new RecordNotFoundException(String.format("User with id: %s not found.", userId)));

      Travelstory travelstory = getTravelstoryByIdOrElseThrow(travelstoryId);

      comment.setTravelstory(travelstory);
      comment.setCreatedAt(new Date());
      comment.setUserId(user.getId());

      commentRepository.save(comment);
   }


   //   GET all comments from a travelstory by travelstoryId
   public List<CommentDTO> getCommentsFromTravelstoryById(Long travelstoryId) {

      Travelstory travelstory = getTravelstoryByIdOrElseThrow(travelstoryId);

      List<Comment> comments = commentRepository.findCommentsByTravelstoryId(travelstory.getId()).orElseThrow(() ->
              new BadRequestException("Comment does not belong to this travelstory."));

      List<CommentDTO> commentDTOList = new ArrayList<>();

      for (Comment comment : comments) {

         User user = userRepository.getById(comment.getUserId());

         CommentDTO commentDTO = new CommentDTO();

         commentDTO.setId(comment.getId());
         commentDTO.setCreatedAt(comment.getCreatedAt());
         commentDTO.setComment(comment.getComment());
         commentDTO.setFullname(user.getFirstname() + " " + user.getLastname());
         commentDTO.setUserId(comment.getUserId());
         commentDTO.profileImage(user.getProfileImage());

         commentDTOList.add(commentDTO);
      }

      return commentDTOList;
   }


   //   GET a comment from a travelstory by commentId and travelstoryId
   public Comment getCommentFromTravelstoryById(Long travelstoryId, Long commentId) {

      Travelstory travelstory = getTravelstoryByIdOrElseThrow(travelstoryId);

      Comment comment = getCommentByIdOrElseThrow(commentId);

      if (!comment.getTravelstory().getId().equals(travelstory.getId())) {
         throw new BadRequestException("Comment does not belong to this travelstory.");
      }

      return comment;
   }


   //   UPDATE a comment by commentId on a travelstory by travelstoryId
   public Comment updateCommitById(Long travelstoryId, Long commentId, Comment comment) {

      Travelstory travelstory = getTravelstoryByIdOrElseThrow(travelstoryId);

      Comment updateComment = getCommentByIdOrElseThrow(commentId);

      if (!updateComment.getTravelstory().getId().equals(travelstory.getId())) {
         throw new BadRequestException("Comment does not belong to this travelstory.");
      }

      updateComment.setComment(comment.getComment());

      return commentRepository.save(updateComment);
   }


   //   DELETE a comment by commentId on a travelstory by travelstoryId
   public String deleteCommitById(Long travelstoryId, Long commentId) {

      Travelstory travelstory = getTravelstoryByIdOrElseThrow(travelstoryId);

      Comment comment = getCommentByIdOrElseThrow(commentId);

      if (!comment.getTravelstory().getId().equals(travelstory.getId())) {
         throw new BadRequestException("Comment does not belong to this travelstory.");
      }

      commentRepository.delete(comment);

      return String.format("Commit with id: %s, on travelstory %s, is successfully deleted.", commentId, travelstoryId);
   }


   private Travelstory getTravelstoryByIdOrElseThrow(Long travelstoryId) {
      return travelstoryRepository.findById(travelstoryId).orElseThrow(() ->
              new RecordNotFoundException(String.format("Travelstory with id: %s not found.", travelstoryId)));
   }


   private Comment getCommentByIdOrElseThrow(Long commentId) {
      return commentRepository.findById(commentId).orElseThrow(() ->
              new RecordNotFoundException(String.format("Comment with id: %s not found.", commentId)));
   }

}
