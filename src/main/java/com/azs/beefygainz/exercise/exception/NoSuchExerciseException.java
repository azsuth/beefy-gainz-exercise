package com.azs.beefygainz.exercise.exception;

import java.util.NoSuchElementException;

public class NoSuchExerciseException extends NoSuchElementException {

    public NoSuchExerciseException(Long exerciseId, String userId) {
        super(String.format("No exercise found with ID %d for user ID %s", exerciseId, userId));
    }
}
