package dev.travelstories.repositories;

import dev.travelstories.entities.Travelstory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelstoryRepository extends JpaRepository<Travelstory, Long> {

}
