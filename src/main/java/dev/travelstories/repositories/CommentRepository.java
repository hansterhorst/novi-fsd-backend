package dev.travelstories.repositories;

import dev.travelstories.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

   Optional<List<Comment>> findCommentsByTravelstoryId(Long travelstoryId);

}
