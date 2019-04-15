package com.azs.beefygainz.exercise.exception;

public class NoSuchExerciseException extends RuntimeException {

    public NoSuchExerciseException(long id) {
        super(String.format("No exercise with ID %d", id));
    }
}
