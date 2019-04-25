package com.azs.beefygainz.exercise.repository;

import com.azs.beefygainz.exercise.model.Exercise;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ExerciseRepository extends CrudRepository<Exercise, Long> {
    Iterable<Exercise> findAllByUserId(String userId);

    Optional<Exercise> findByIdAndUserId(Long id, String userId);

    @Query(
            "SELECT DISTINCT e FROM Exercise e JOIN FETCH e.sets s WHERE e.userId = :userId AND s.created > :created"
    )
    Iterable<Exercise> findAllCurrentByUserId(@Param("userId") String userId, @Param("created") LocalDateTime created);
}
