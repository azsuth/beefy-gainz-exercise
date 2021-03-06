package com.azs.beefygainz.exercise.service;

import com.azs.beefygainz.exercise.exception.NoSuchExerciseException;
import com.azs.beefygainz.exercise.model.Exercise;
import com.azs.beefygainz.exercise.model.Set;
import com.azs.beefygainz.exercise.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final int workoutDuration;

    @Autowired
    public ExerciseServiceImpl(ExerciseRepository exerciseRepository,
                               @Value("${workout.duration:4}") int workoutDuration) {
        this.exerciseRepository = exerciseRepository;
        this.workoutDuration = workoutDuration;
    }

    @Override
    public List<Exercise> getAll() {
        List<Exercise> exercises = new ArrayList<>();

        exerciseRepository.findAll().forEach(exercises::add);

        return exercises;
    }

    @Override
    public List<Exercise> getAll(String userId, boolean current, String search) {
        List<Exercise> exercises = new ArrayList<>();

        if (current) {
            LocalDateTime workoutStart = LocalDateTime.now().minusHours(workoutDuration);

            exerciseRepository.findAllCurrentByUserId(userId, workoutStart)
                    .forEach(exercise -> {
                        exercise.getSets().removeIf(set -> set.getCreated().isBefore(workoutStart));
                        exercises.add(exercise);
                    });

            exercises.forEach(exercise -> exercise.getSets().sort((set1, set2) -> {
                if (set1.getCreated().isEqual(set2.getCreated())) {
                    return 0;
                }

                return set1.getCreated().isBefore(set2.getCreated()) ? 1 : -1;
            }));

            exercises.sort((exercise1, exercise2) -> {
                Set set1 = exercise1.getSets().get(exercise1.getSets().size() - 1);
                Set set2 = exercise2.getSets().get(exercise2.getSets().size() - 1);

                if (set1.getCreated().isEqual(set2.getCreated())) {
                    return 0;
                }

                return set1.getCreated().isBefore(set2.getCreated()) ? 1 : -1;
            });
        } else if (search != null) {
            exerciseRepository.findAllByUserIdAndNameContainingIgnoreCase(userId, search).forEach(exercises::add);
        } else {
            exerciseRepository.findAllByUserId(userId).forEach(exercises::add);
        }

        return exercises;
    }

    @Override
    public Exercise create(Exercise exercise, String userId) {
        if (exercise.isNew()) {
            exercise.setCreated(LocalDateTime.now());
            exercise.setUpdated(LocalDateTime.now());
            exercise.setUserId(userId);

            Set defaultSet = new Set();
            defaultSet.setCreated(LocalDateTime.now());
            defaultSet.setUpdated(LocalDateTime.now());

            exercise.addSet(defaultSet);

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
