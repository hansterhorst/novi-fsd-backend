package dev.travelstories.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "Travelstory")
@Table(name = "travelstories")
public class Travelstory {

   @Id
   @SequenceGenerator(name = "travelstory_id_sequence", sequenceName = "travelstory_id_sequence", allocationSize = 1)
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "travelstory_id_sequence")
   @Column(name = "travelstory_id", nullable = false, updatable = false)
   private Long id;

   @Column(length = 100)
   private String title;

   private String author;

   @Column(columnDefinition = "TEXT")
   private String article;

   private Date tripDate;

   private String tripType;

   private String country;

   private Boolean isPublic;

   private String imageUrl;


   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_travelstory_user_key"))
   @JsonBackReference // to solve the infinite recursion problem
   private User user;


   @OneToMany(mappedBy = "travelstory", cascade = CascadeType.ALL, orphanRemoval = true)
   /*
    * @JsonManagedReference, to solve the infinite recursion problem
    * is the forward part of reference – the one that gets serialized normally.
    * */
   @JsonManagedReference
   private List<Comment> comments;


   /*
    * CONSTRUCTORS
    * */

   public Travelstory() {
   }

   public Travelstory(Long id, String title, String author, String article, Date tripDate, String tripType, String country, Boolean isPublic, String imageUrl, User user, List<Comment> comments ) {
      this.id = id;
      this.title = title;
      this.author = author;
      this.article = article;
      this.tripDate = tripDate;
      this.tripType = tripType;
      this.country = country;
      this.isPublic = isPublic;
      this.imageUrl = imageUrl;
      this.user = user;
      this.comments = comments;
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

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getAuthor() {
      return author;
   }

   public void setAuthor(String author) {
      this.author = author;
   }

   public String getArticle() {
      return article;
   }

   public void setArticle(String article) {
      this.article = article;
   }

   public Date getTripDate() {
      return tripDate;
   }

   public void setTripDate(Date tripDate) {
      this.tripDate = tripDate;
   }

   public String getTripType() {
      return tripType;
   }

   public void setTripType(String tripType) {
      this.tripType = tripType;
   }

   public String getCountry() {
      return country;
   }

   public void setCountry(String country) {
      this.country = country;
   }

   public Boolean getIsPublic() {
      return isPublic;
   }

   public void setIsPublic(Boolean isPublic) {
      this.isPublic = isPublic;
   }

   public String getImageUrl() {
      return imageUrl;
   }

   public void setImageUrl(String imageUrl) {
      this.imageUrl = imageUrl;
   }

   public User getUser() {
      return user;
   }

   public void setUser(User user) {
      this.user = user;
   }

   public List<Comment> getComments() {
      return comments;
   }

   public void setComments(List<Comment> comments) {
      this.comments = comments;
   }
}
