package dev.travelstories.repositories;

import dev.travelstories.entities.Travelstory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelstoryRepository extends JpaRepository<Travelstory, Long> {

   // custom query
   @Query("SELECT t FROM Travelstory t WHERE t.isPublic = true")
   List<Travelstory> findAllPublicTravelstories();

   List<Travelstory> findTravelstoriesByUserId(Long userId);
}
