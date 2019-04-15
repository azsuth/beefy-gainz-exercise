package com.azs.beefygainz.exercise.service;

import com.azs.beefygainz.exercise.model.Exercise;
import com.azs.beefygainz.exercise.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseServiceImpl(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public List<Exercise> findAllByUserId(long userId) {
        return null;
    }

    @Override
    public Exercise save(Exercise exercise) {
        return null;
    }

    @Override
    public Exercise findById(long id) {
        return null;
    }
}
