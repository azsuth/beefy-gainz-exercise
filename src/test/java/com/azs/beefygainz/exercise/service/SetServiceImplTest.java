package com.azs.beefygainz.exercise.service;

import com.azs.beefygainz.exercise.exception.NoSuchExerciseException;
import com.azs.beefygainz.exercise.model.Exercise;
import com.azs.beefygainz.exercise.model.Set;
import com.azs.beefygainz.exercise.repository.ExerciseRepository;
import com.azs.beefygainz.exercise.repository.SetRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class SetServiceImplTest {

    @Mock
    ExerciseRepository exerciseRepositoryMock;
    @Mock
    SetRepository setRepositoryMock;

    @InjectMocks
    SetServiceImpl setService;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void saveNew() {
        Set setSpy = spy(Set.builder().build());

        when(exerciseRepositoryMock.findById(any())).thenReturn(Optional.of(Exercise.builder().build()));
        when(setRepositoryMock.save(setSpy)).thenReturn(setSpy);

        Set set = setService.save(setSpy, 1L);

        assertNotNull(set.getExercise());
        verify(setSpy).setExercise(any());
        verify(setSpy).setCreated(any());
        verify(setSpy).setUpdated(any());
        verify(setRepositoryMock).save(any());
    }

    @Test
    public void saveExisting() {
        Set setSpy = spy(Set.builder().build());

        when(exerciseRepositoryMock.findById(any())).thenReturn(Optional.of(Exercise.builder().build()));
        when(setRepositoryMock.findById(any())).thenReturn(Optional.of(Set.builder().build()));

        setService.save(setSpy, 1L);

        verify(setSpy, times(0)).setCreated(any());
    }

    @Test(expected = NoSuchExerciseException.class)
    public void saveNoExercise() {
        when(exerciseRepositoryMock.findById(any())).thenReturn(Optional.empty());

        setService.save(Set.builder().build(), 1L);
    }
}