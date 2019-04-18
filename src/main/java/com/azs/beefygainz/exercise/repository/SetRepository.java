package com.azs.beefygainz.exercise.repository;

import com.azs.beefygainz.exercise.model.Exercise;
import com.azs.beefygainz.exercise.model.Set;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SetRepository extends CrudRepository<Set, Long> {

    Optional<Set> findByIdAndExercise(Long id, Exercise exercise);
}
