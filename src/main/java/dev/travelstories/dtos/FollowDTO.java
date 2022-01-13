package dev.travelstories.dtos;

import dev.travelstories.entities.Follow;

public class FollowDTO {

   private Long id;
   private Long authUserId;
   private Long followUserId;
   private String followUserFullname;
   private String followUserProfileImage;

   public FollowDTO() {
   }

   public FollowDTO(Long id, Long authUserId, Long followUserId, String followUserFullname, String followUserProfileImage) {
      this.id = id;
      this.authUserId = authUserId;
      this.followUserId = followUserId;
      this.followUserFullname = followUserFullname;
      this.followUserProfileImage = followUserProfileImage;
   }

   public static FollowDTO dtoToEntity() {
      return null;
   }

   public static FollowDTO entityToDTO(Follow follow) {

      FollowDTO followDTO = new FollowDTO();
      followDTO.setId(follow.getId());
      followDTO.setAuthUserId(follow.getAuthUserId());
      followDTO.setFollowUserId(follow.getFollowUser().getId());
      followDTO.setFollowUserFullname(follow.getFollowUser().getFirstname() + " " + follow.getFollowUser().getLastname());
      followDTO.setFollowUserProfileImage(follow.getFollowUser().getProfileImage());

      return followDTO;
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

   public Long getFollowUserId() {
      return followUserId;
   }

   public void setFollowUserId(Long followUserId) {
      this.followUserId = followUserId;
   }

   public String getFollowUserFullname() {
      return followUserFullname;
   }

   public void setFollowUserFullname(String followUserFullname) {
      this.followUserFullname = followUserFullname;
   }

   public String getFollowUserProfileImage() {
      return followUserProfileImage;
   }

   public void setFollowUserProfileImage(String followUserProfileImage) {
      this.followUserProfileImage = followUserProfileImage;
   }
}
