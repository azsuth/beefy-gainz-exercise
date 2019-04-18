package com.azs.beefygainz.exercise.service;

import com.azs.beefygainz.exercise.model.Exercise;
import com.azs.beefygainz.exercise.repository.ExerciseRepository;
import com.azs.beefygainz.exercise.repository.SetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final SetRepository setRepository;

    @Autowired
    public ExerciseServiceImpl(ExerciseRepository exerciseRepository, SetRepository setRepository) {
        this.exerciseRepository = exerciseRepository;
        this.setRepository = setRepository;
    }

    @Override
    public List<Exercise> findAllByUserId(String userId) {
        List<Exercise> exercises = new ArrayList<>();

        exerciseRepository.findAllByUserId(userId).forEach(exercises::add);

        return exercises;
    }

    @Override
    public Exercise save(Exercise exercise, String userId) {
        if (exercise.getId() == null || !exerciseRepository.findById(exercise.getId()).isPresent()) {
            exercise.setUserId(userId);
            exercise.setCreated(LocalDateTime.now());
        }

        exercise.setUpdated(LocalDateTime.now());

        exercise.getSets().forEach(set -> {
            if (!setRepository.findById(set.getId()).isPresent()) {
                set.setCreated(LocalDateTime.now());
            }

            set.setUpdated(LocalDateTime.now());
        });

        return exerciseRepository.save(exercise);
    }
}
