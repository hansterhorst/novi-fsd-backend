package dev.travelstories.entities;

import javax.persistence.*;

@Entity(name = "Role")
@Table(name = "roles")
public class Role {

   @Id
   @SequenceGenerator(name = "role_id_sequence", sequenceName = "role_id_sequence", allocationSize = 1)
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_sequence")
   @Column(name = "role_id", nullable = false, updatable = false)
   private Long id;

   @Column(name = "role_name", length = 60)
   private String name;


   public Role() {
   }

   public Role(Long id, String name) {
      this.id = id;
      this.name = name;
   }


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

}
