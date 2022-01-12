package dev.travelstories.repositories;

import dev.travelstories.entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

   Optional<List<Like>> findByTravelstoryId(Long Long);

}
