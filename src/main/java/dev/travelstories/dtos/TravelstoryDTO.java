package dev.travelstories.dtos;

import dev.travelstories.entities.Travelstory;

import java.util.Date;

public class TravelstoryDTO {

   private Long id;
   private String title;
   private String author;
   private String article;
   private Date tripDate;
   private String tripType;
   private String country;
   private Boolean isPublic;
   private String imageUrl;
   private Long userId;

   public TravelstoryDTO() {
   }

   public TravelstoryDTO(Long id, String title, String author, String article, Date tripDate, String tripType, String country, Boolean isPublic, String imageUrl, Long userId) {
      this.id = id;
      this.title = title;
      this.author = author;
      this.article = article;
      this.tripDate = tripDate;
      this.tripType = tripType;
      this.country = country;
      this.isPublic = isPublic;
      this.imageUrl = imageUrl;
      this.userId = userId;
   }

   public static TravelstoryDTO entityToDTO(Travelstory travelstory) {

      TravelstoryDTO travelstoryDTO = new TravelstoryDTO();
      travelstoryDTO.setId(travelstory.getId());
      travelstoryDTO.setTitle(travelstory.getTitle());
      travelstoryDTO.setAuthor(travelstory.getAuthor());
      travelstoryDTO.setArticle(travelstory.getArticle());
      travelstoryDTO.setTripDate(travelstory.getTripDate());
      travelstoryDTO.setTripType(travelstory.getTripType());
      travelstoryDTO.setCountry(travelstory.getCountry());
      travelstoryDTO.setIsPublic(travelstory.getIsPublic());
      travelstoryDTO.setImageUrl(travelstory.getImageUrl());
      travelstoryDTO.setUserId(travelstory.getUser().getId());

      return travelstoryDTO;
   }


   public static Travelstory dtoToEntity(TravelstoryDTO travelstoryDTO) {

      Travelstory travelstory = new Travelstory();
      travelstory.setId(travelstoryDTO.getId());
      travelstory.setTitle(travelstoryDTO.getTitle());
      travelstory.setAuthor(travelstoryDTO.getAuthor());
      travelstory.setArticle(travelstoryDTO.getArticle());
      travelstory.setTripDate(travelstoryDTO.getTripDate());
      travelstory.setTripType(travelstoryDTO.getTripType());
      travelstory.setCountry(travelstoryDTO.getCountry());
      travelstory.setIsPublic(travelstoryDTO.getIsPublic());
      travelstory.setImageUrl(travelstoryDTO.getImageUrl());

      return travelstory;
   }


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

   public Long getUserId() {
      return userId;
   }

   public void setUserId(Long userId) {
      this.userId = userId;
   }
}
