package dev.travelstories.dtos;

import dev.travelstories.entities.Comment;
import dev.travelstories.entities.User;

import javax.validation.constraints.Size;
import java.util.Date;

public class CommentDTO {


   private Long id;
   @Size(min = 2, max = 300, message = "Reactie bevat 2 tot 300 characters")
   private String comment;
   private Date createdAt;
   private String fullname;
   private Long userId;
   private String profileImage;


   public CommentDTO() {
   }

   public CommentDTO(Long id, Date createdAt, String comment, String fullname, Long userId, String profileImage
   ) {
      this.id = id;
      this.createdAt = createdAt;
      this.comment = comment;
      this.fullname = fullname;
      this.userId = userId;
      this.profileImage = profileImage;
   }


   //   Map entity to dto
   public static CommentDTO entityToDTO(Comment comment) {

      User user = new User();
      CommentDTO commentDTO = new CommentDTO();

      commentDTO.setId(comment.getId());
      commentDTO.setCreatedAt(comment.getCreatedAt());
      commentDTO.setComment(comment.getComment());
      commentDTO.setFullname(user.getFirstname() + " " + user.getLastname());
      commentDTO.setUserId(user.getId());
      commentDTO.profileImage(user.getProfileImage());

      return commentDTO;
   }


   //   Map dto to entity
   public static Comment dtoToEntity(CommentDTO commentDTO) {

      Comment comment = new Comment();

      comment.setId(commentDTO.getId());
      comment.setCreatedAt(commentDTO.getCreatedAt());
      comment.setComment(commentDTO.getComment());
      comment.setUserId(commentDTO.getUserId());

      return comment;
   }


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Date getCreatedAt() {
      return createdAt;
   }

   public void setCreatedAt(Date createdAt) {
      this.createdAt = createdAt;
   }

   public String getComment() {
      return comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }

   public String getFullname() {
      return fullname;
   }

   public void setFullname(String fullname) {
      this.fullname = fullname;
   }

   public Long getUserId() {
      return userId;
   }

   public void setUserId(Long userId) {
      this.userId = userId;
   }

   public String profileImage() {
      return profileImage;
   }

   public void profileImage(String profileImage
   ) {
      this.profileImage = profileImage
      ;
   }
}
