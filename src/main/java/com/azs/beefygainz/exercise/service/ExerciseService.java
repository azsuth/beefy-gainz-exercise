package com.azs.beefygainz.exercise.service;

import com.azs.beefygainz.exercise.model.Exercise;

import java.util.List;

public interface ExerciseService {
    List<Exercise> findAllByUserId(String userId);

    Exercise create(Exercise exercise, String userId);

    Exercise update(Long exerciseId, Exercise exercise, String userId);

    void delete(Long exerciseId, String userId);
}
