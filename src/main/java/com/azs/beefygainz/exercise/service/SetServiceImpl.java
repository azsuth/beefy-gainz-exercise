package com.azs.beefygainz.exercise.service;

import com.azs.beefygainz.exercise.exception.NoSuchSetException;
import com.azs.beefygainz.exercise.model.Exercise;
import com.azs.beefygainz.exercise.model.Set;
import com.azs.beefygainz.exercise.repository.SetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SetServiceImpl implements SetService {

    private final SetRepository setRepository;
    private final ExerciseService exerciseService;

    @Autowired
    public SetServiceImpl(SetRepository setRepository, ExerciseService exerciseService) {
        this.setRepository = setRepository;
        this.exerciseService = exerciseService;
    }

    @Override
    public Set create(Set set, Long exerciseId, String userId) {
        Exercise savedExercise = exerciseService.getSavedExercise(exerciseId, userId);

        if (set.isNew()) {
            set.setCreated(LocalDateTime.now());
            set.setUpdated(LocalDateTime.now());
            set.setExercise(savedExercise);

            return setRepository.save(set);
        } else {
            return update(set.getId(), set, savedExercise);
        }
    }

    @Override
    public Set update(Long setId, Set set, Long exerciseId, String userId) {
        return update(setId, set, exerciseService.getSavedExercise(exerciseId, userId));
    }

    public Set update(Long setId, Set set, Exercise exercise) {
        Set savedSet = getSavedSet(setId, exercise);

        savedSet.setReps(set.getReps());
        savedSet.setLbs(set.getLbs());
        savedSet.setNotes(set.getNotes());
        savedSet.setUpdated(LocalDateTime.now());

        return setRepository.save(savedSet);
    }

    @Override
    public void delete(Long setId, Long exerciseId, String userId) {
        setRepository.delete(getSavedSet(setId, exerciseService.getSavedExercise(exerciseId, userId)));
    }

    public Set getSavedSet(Long setId, Exercise exercise) {
        return setRepository.findByIdAndExercise(setId, exercise)
                .orElseThrow(() -> new NoSuchSetException(setId));
    }
}
