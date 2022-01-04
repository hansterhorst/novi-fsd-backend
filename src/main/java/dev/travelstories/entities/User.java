package dev.travelstories.entities;

import javax.persistence.*;


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


   /*
    * CONSTRUCTORS
    * */

   public User() {
   }

   public User(Long id, String firstname, String lastname, String username, String email, String password) {
      this.id = id;
      this.firstname = firstname;
      this.lastname = lastname;
      this.username = username;
      this.email = email;
      this.password = password;
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

}



