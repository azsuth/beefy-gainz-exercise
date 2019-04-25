package com.azs.beefygainz.exercise.service;

import com.azs.beefygainz.exercise.exception.NoSuchExerciseException;
import com.azs.beefygainz.exercise.model.Exercise;
import com.azs.beefygainz.exercise.model.Set;
import com.azs.beefygainz.exercise.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public Exercise create(Exercise exercise, String userId) {
        if (exercise.isNew()) {
            exercise.setCreated(LocalDateTime.now());
            exercise.setUpdated(LocalDateTime.now());
            exercise.setUserId(userId);

            exercise.addSet(Set.builder().build());

            return exerciseRepository.save(exercise);
        } else {
            return update(exercise.getId(), exercise, userId);
        }
    }

    @Override
    public Exercise update(Long exerciseId, Exercise exercise, String userId) {
        Exercise savedExercise = getSavedExercise(exerciseId, userId);

        savedExercise.setName(exercise.getName());
        savedExercise.setNotes(exercise.getNotes());
        savedExercise.setUpdated(LocalDateTime.now());

        return exerciseRepository.save(savedExercise);
    }

    @Override
    public void delete(Long exerciseId, String userId) {
        exerciseRepository.delete(getSavedExercise(exerciseId, userId));
    }

    @Override
    public Exercise getSavedExercise(Long exerciseId, String userId) {
        return exerciseRepository.findByIdAndUserId(exerciseId, userId)
                .orElseThrow(() -> new NoSuchExerciseException(exerciseId, userId));
    }
}
