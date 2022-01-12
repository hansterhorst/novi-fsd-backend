package dev.travelstories.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity(name = "Like")
@Table(name = "likes")
public class Like {

   @Id
   @SequenceGenerator(name = "like_id_sequence", sequenceName = "like_id_sequence", allocationSize = 1)
   @GeneratedValue(generator = "like_id_sequence", strategy = GenerationType.SEQUENCE)
   @Column(name = "like_id", nullable = false, updatable = false)
   private Long id;

   private Long userId;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "travelstory_id", referencedColumnName = "travelstory_id", nullable = false, foreignKey = @ForeignKey(name = "FK_like_travelstory_key"))
   @JsonBackReference
   private Travelstory travelstory;


   /*
    * CONSTRUCTORS
    * */

   public Like() {
   }

   public Like(Long id, Long userId, Travelstory travelstory) {
      this.id = id;
      this.userId = userId;
      this.travelstory = travelstory;
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

   public Long getUserId() {
      return userId;
   }

   public void setUserId(Long userId) {
      this.userId = userId;
   }

   public Travelstory getTravelstory() {
      return travelstory;
   }

   public void setTravelstory(Travelstory travelstory) {
      this.travelstory = travelstory;
   }
}
