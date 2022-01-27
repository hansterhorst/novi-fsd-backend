package dev.travelstories.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity(name = "Follow")
@Table(name = "followers")
public class Follow {


   @Id
   @SequenceGenerator(name = "follow_id_sequence", sequenceName = "follow_id_sequence", allocationSize = 1)
   @GeneratedValue(generator = "follow_id_sequence", strategy = GenerationType.SEQUENCE)
   @Column(name = "follow_id", nullable = false, updatable = false)
   private Long id;

   private Long authUserId;

   //   User who is follow you
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "follow_user_id", referencedColumnName = "user_id", nullable = false,
           foreignKey = @ForeignKey(name = "FK_follow_user_key"))
   @JsonBackReference
   private User followUser;


   public Follow() {
   }

   public Follow(Long id, Long authUserId, User followUser) {
      this.id = id;
      this.authUserId = authUserId;
      this.followUser = followUser;
   }


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getAuthUserId() {
      return authUserId;
   }

   public void setAuthUserId(Long authUserId) {
      this.authUserId = authUserId;
   }

   public User getFollowUser() {
      return followUser;
   }

   public void setFollowUser(User followUser) {
      this.followUser = followUser;
   }
}
