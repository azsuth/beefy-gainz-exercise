package com.azs.beefygainz.exercise.service;

import com.azs.beefygainz.exercise.model.Exercise;
import com.azs.beefygainz.exercise.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseServiceImpl(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public List<Exercise> findAllByUserId(String userId) {
        List<Exercise> exercises = new ArrayList<>();

        exerciseRepository.findAllByUserId(userId).forEach(exercises::add);

        return exercises;
    }

    @Override
    public Exercise save(Exercise exercise, String userId) {
        exercise.setUserId(userId);
        return exerciseRepository.save(exercise);
    }
}
