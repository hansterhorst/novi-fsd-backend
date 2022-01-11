package dev.travelstories.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Comment")
@Table(name = "comments")
public class Comment {

   @Id
   @SequenceGenerator(name = "comment_id_sequence", sequenceName = "comment_id_sequence", allocationSize = 1)
   @GeneratedValue(generator = "comment_id_sequence", strategy = GenerationType.SEQUENCE)
   @Column(name = "comment_id", nullable = false, updatable = false)
   private Long id;
   private Date createdAt;
   private String comment;
   private Long userId;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "travelstory_id", referencedColumnName = "travelstory_id", nullable = false, foreignKey = @ForeignKey(name = "FK_comment_travelstory_key"))
   /*
    * @JsonBackReference, to solve the infinite recursion problem
    * is the back part of reference – it will be omitted from serialization.
    * */
   @JsonBackReference
   public Travelstory travelstory;



   /*
    * CONSTRUCTORS
    * */

   public Comment() {
   }

   public Comment(Long id, String comment, Travelstory travelstory, Long userId) {
      this.id = id;
      this.comment = comment;
      this.createdAt = new Date();
      this.travelstory = travelstory;
      this.userId = userId;
   }


   /*
    * GETTERS & SETTERS
    * */

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

   public Travelstory getTravelstory() {
      return travelstory;
   }

   public void setTravelstory(Travelstory travelstory) {
      this.travelstory = travelstory;
   }

   public Long getUserId() {
      return userId;
   }

   public void setUserId(Long userId) {
      this.userId = userId;
   }
}