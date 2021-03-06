package dev.travelstories.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.*;


@Entity(name = "User")
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"username", "email"})})
public class User {

   @Id
   @SequenceGenerator(name = "user_id_sequence", sequenceName = "user_id_sequence", allocationSize = 1)
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_sequence")
   @Column(name = "user_id", nullable = false, updatable = false)
   private Long id;
   private String firstname;
   private String lastname;
   private String username;
   private String email;
   private String password;
   private String profileImage;
   private String city;
   private String country;
   @Column(columnDefinition = "TEXT")
   private String bio;


   @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
   @JsonManagedReference // to solve the infinite recursion problem
   private List<Travelstory> travelstories = new ArrayList<>();


   //   You're following other users
   @OneToMany(mappedBy = "followUser", cascade = CascadeType.ALL, orphanRemoval = true)
   @JsonManagedReference
   private List<Follow> follows = new ArrayList<>();


   @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
   @JoinTable(name = "user_roles",
           joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
           inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id")
   )
   private List<Role> roles = new ArrayList<>();


   public User() {
   }

   public User(Long id, String firstname, String lastname, String username, String email, String password, String profileImage, String city, String country, String bio) {
      this.id = id;
      this.firstname = firstname;
      this.lastname = lastname;
      this.username = username;
      this.email = email;
      this.password = password;
      this.profileImage = profileImage;
      this.city = city;
      this.country = country;
      this.bio = bio;
   }


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getFirstname() {
      return firstname;
   }

   public void setFirstname(String firstname) {
      this.firstname = firstname;
   }

   public String getLastname() {
      return lastname;
   }

   public void setLastname(String lastname) {
      this.lastname = lastname;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public List<Travelstory> getTravelstories() {
      return travelstories;
   }

   public void setTravelstories(List<Travelstory> travelstories) {
      this.travelstories = travelstories;
   }

   public String getProfileImage() {
      return profileImage;
   }

   public void setProfileImage(String profileImage) {
      this.profileImage = profileImage;
   }

   public List<Follow> getFollows() {
      return follows;
   }

   public void setFollows(List<Follow> follows) {
      this.follows = follows;
   }

   public String getCity() {
      return city;
   }

   public void setCity(String city) {
      this.city = city;
   }

   public String getCountry() {
      return country;
   }

   public void setCountry(String country) {
      this.country = country;
   }

   public String getBio() {
      return bio;
   }

   public void setBio(String bio) {
      this.bio = bio;
   }

   public List<Role> getRoles() {
      return roles;
   }

   public void setRoles(List<Role> roles) {
      this.roles = roles;
   }
}



