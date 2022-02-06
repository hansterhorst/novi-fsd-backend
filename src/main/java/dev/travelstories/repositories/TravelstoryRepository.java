package dev.travelstories.repositories;

import dev.travelstories.entities.Travelstory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TravelstoryRepository extends JpaRepository<Travelstory, Long> {

   @Query("SELECT t FROM Travelstory t WHERE t.isPublic = true")
   Optional<List<Travelstory>> findAllPublicTravelstories();

   List<Travelstory> findTravelstoriesByUserId(Long userId);
}
