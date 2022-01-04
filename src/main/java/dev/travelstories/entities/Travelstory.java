package dev.travelstories.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;

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

   private Boolean isPrivate;

   private String imageUrl;


   /*
    * CONSTRUCTORS
    * */

   public Travelstory() {
   }

   public Travelstory(Long id, String title, String author, String article, Date tripDate, String tripType, String country, Boolean isPrivate, String imageUrl ) {
      this.id = id;
      this.title = title;
      this.author = author;
      this.article = article;
      this.tripDate = tripDate;
      this.tripType = tripType;
      this.country = country;
      this.isPrivate = isPrivate;
      this.imageUrl = imageUrl;
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

   public Boolean getIsPrivate() {
      return isPrivate;
   }

   public void setIsPrivate(Boolean isPrivate) {
      this.isPrivate = isPrivate;
   }

   public String getImageUrl() {
      return imageUrl;
   }

   public void setImageUrl(String imageUrl) {
      this.imageUrl = imageUrl;
   }

}
