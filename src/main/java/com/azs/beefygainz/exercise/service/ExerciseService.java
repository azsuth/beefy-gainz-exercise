package com.azs.beefygainz.exercise.service;

import com.azs.beefygainz.exercise.model.Exercise;

import java.util.List;

public interface ExerciseService {
    List<Exercise> findAllByUserId(long userId);

    Exercise save(Exercise exercise);

    Exercise findById(long id);
}
