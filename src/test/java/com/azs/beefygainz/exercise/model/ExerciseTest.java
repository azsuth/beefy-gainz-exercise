package com.azs.beefygainz.exercise.model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class ExerciseTest {

    @Test
    public void addSet() {
        Exercise exerciseSpy = spy(Exercise.builder().name("Bench Press").sets(new ArrayList<>()).build());
        Set setSpy = spy(Set.builder().lbs(115).reps(12).build());

        exerciseSpy.addSet(setSpy);

        assertNotNull(setSpy.getExercise());
        verify(setSpy).setExercise(any());
    }
}