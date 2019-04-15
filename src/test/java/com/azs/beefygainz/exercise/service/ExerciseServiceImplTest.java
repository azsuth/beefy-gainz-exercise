package com.azs.beefygainz.exercise.service;

import com.azs.beefygainz.exercise.model.Exercise;
import com.azs.beefygainz.exercise.repository.ExerciseRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ExerciseServiceImplTest {

    @Mock
    ExerciseRepository exerciseRepositoryMock;

    @InjectMocks
    ExerciseServiceImpl exerciseService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void findAllByUserId() {
        fail();
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
        fail();
    }
}