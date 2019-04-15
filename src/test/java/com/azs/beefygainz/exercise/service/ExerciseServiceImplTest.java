package com.azs.beefygainz.exercise.service;

import com.azs.beefygainz.exercise.exception.NoSuchExerciseException;
import com.azs.beefygainz.exercise.model.Exercise;
import com.azs.beefygainz.exercise.repository.ExerciseRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
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

        when(exerciseRepositoryMock.findAllByUserId(anyLong())).thenReturn(mockExercises);

        List<Exercise> exercises = exerciseService.findAllByUserId(1L);

        assertEquals(2, exercises.size());
        verify(exerciseRepositoryMock).findAllByUserId(1L);
    }

    @Test
    public void save() {
        when(exerciseRepositoryMock.save(any())).thenReturn(Exercise.builder().build());

        Exercise exercise = exerciseService.save(Exercise.builder().build());

        assertNotNull(exercise);
        verify(exerciseRepositoryMock).save(any());
    }

    @Test
    public void findById() {
        when(exerciseRepositoryMock.findById(anyLong())).thenReturn(Optional.of(Exercise.builder().build()));

        Exercise exercise = exerciseService.findById(1L);

        assertNotNull(exercise);
        verify(exerciseRepositoryMock).findById(1L);
    }

    @Test(expected = NoSuchExerciseException.class)
    public void findByIdNotFound() {
        when(exerciseRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());

        exerciseService.findById(1L);
    }
}