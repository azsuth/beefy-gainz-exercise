package com.azs.beefygainz.exercise.repository;

import com.azs.beefygainz.exercise.model.Exercise;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ExerciseRepository extends CrudRepository<Exercise, Long> {
    Iterable<Exercise> findAllByUserId(String userId);

    Optional<Exercise> findByIdAndUserId(Long id, String userId);
}
