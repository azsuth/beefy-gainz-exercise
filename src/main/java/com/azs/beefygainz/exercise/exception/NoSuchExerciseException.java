package com.azs.beefygainz.exercise.exception;

import java.util.NoSuchElementException;

public class NoSuchExerciseException extends NoSuchElementException {

    public NoSuchExerciseException(Long exerciseId) {
        super(String.format("No exercise found with exercise ID %d", exerciseId));
    }
}
