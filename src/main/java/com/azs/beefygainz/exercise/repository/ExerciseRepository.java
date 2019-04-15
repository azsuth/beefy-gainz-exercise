package com.azs.beefygainz.exercise.repository;

import com.azs.beefygainz.exercise.model.Exercise;
import org.springframework.data.repository.CrudRepository;

public interface ExerciseRepository extends CrudRepository<Exercise, Long> {
    Iterable<Exercise> findAllByUserId(long userId);
}
