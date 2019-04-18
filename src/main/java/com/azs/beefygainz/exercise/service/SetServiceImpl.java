package com.azs.beefygainz.exercise.service;

import com.azs.beefygainz.exercise.exception.NoSuchExerciseException;
import com.azs.beefygainz.exercise.model.Exercise;
import com.azs.beefygainz.exercise.model.Set;
import com.azs.beefygainz.exercise.repository.ExerciseRepository;
import com.azs.beefygainz.exercise.repository.SetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SetServiceImpl implements SetService {

    private final ExerciseRepository exerciseRepository;
    private final SetRepository setRepository;

    @Autowired
    public SetServiceImpl(ExerciseRepository exerciseRepository, SetRepository setRepository) {
        this.exerciseRepository = exerciseRepository;
        this.setRepository = setRepository;
    }

    @Override
    public Set save(Set set, Long exerciseId) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new NoSuchExerciseException(exerciseId));

        if (!setRepository.findById(set.getId()).isPresent()) {
            set.setCreated(LocalDateTime.now());
        }

        set.setExercise(exercise);
        set.setUpdated(LocalDateTime.now());

        return setRepository.save(set);
    }
}
