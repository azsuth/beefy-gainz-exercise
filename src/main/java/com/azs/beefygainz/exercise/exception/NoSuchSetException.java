package com.azs.beefygainz.exercise.exception;

import java.util.NoSuchElementException;

public class NoSuchSetException extends NoSuchElementException {

    public NoSuchSetException(Long setId) {
        super(String.format("No set found with ID %ds", setId));
    }
}
