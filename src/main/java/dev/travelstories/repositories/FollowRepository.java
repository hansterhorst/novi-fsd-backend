package dev.travelstories.repositories;

import dev.travelstories.entities.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

   Optional<List<Follow>> findByFollowUserId(Long followUserId);

}

