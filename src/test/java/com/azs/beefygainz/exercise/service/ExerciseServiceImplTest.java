package com.azs.beefygainz.exercise.service;

import com.azs.beefygainz.exercise.model.Exercise;
import com.azs.beefygainz.exercise.repository.ExerciseRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class ExerciseServiceImplTest {

    @Mock
    ExerciseRepository exerciseRepositoryMock;

    @InjectMocks
    ExerciseServiceImpl exerciseService;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void findAllByUserId() {
        List<Exercise> mockExercises = new ArrayList<>();
        mockExercises.add(Exercise.builder().build());
        mockExercises.add(Exercise.builder().build());

        when(exerciseRepositoryMock.findAllByUserId(anyString())).thenReturn(mockExercises);

        List<Exercise> exercises = exerciseService.findAllByUserId("asdf");

        assertEquals(2, exercises.size());
        verify(exerciseRepositoryMock).findAllByUserId("asdf");
    }

    @Test
    public void save() {
        Exercise exerciseSpy = spy(Exercise.builder().name("Bench Press").build());

        when(exerciseRepositoryMock.save(any())).thenReturn(exerciseSpy);

        Exercise exercise = exerciseService.save(exerciseSpy, "asdf");

        assertNotNull(exercise);
        verify(exerciseRepositoryMock).save(any());
        verify(exerciseSpy).setUserId("asdf");
    }
}