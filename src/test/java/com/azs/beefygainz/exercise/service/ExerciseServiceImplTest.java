package com.azs.beefygainz.exercise.service;

import com.azs.beefygainz.exercise.model.Exercise;
import com.azs.beefygainz.exercise.model.Set;
import com.azs.beefygainz.exercise.repository.ExerciseRepository;
import com.azs.beefygainz.exercise.repository.SetRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class ExerciseServiceImplTest {

    @Mock
    ExerciseRepository exerciseRepositoryMock;

    @Mock
    SetRepository setRepositoryMock;

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
        Exercise exerciseSpy = spy(Exercise.builder().name("Bench Press").sets(new ArrayList<>()).build());

        when(exerciseRepositoryMock.save(any())).thenReturn(exerciseSpy);

        Exercise exercise = exerciseService.save(exerciseSpy, "asdf");

        assertNotNull(exercise);
        verify(exerciseRepositoryMock).save(any());
        verify(exerciseSpy).setUserId("asdf");
    }

    @Test
    public void saveNew() {
        Exercise exerciseSpy = spy(Exercise.builder().name("Bench Press").sets(new ArrayList<>()).build());

        exerciseService.save(exerciseSpy, "asdf");

        verify(exerciseSpy).setCreated(any());
        verify(exerciseSpy).setUpdated(any());
    }

    @Test
    public void saveExisting() {
        Exercise exerciseSpy = spy(Exercise.builder().name("Bench Press").sets(new ArrayList<>()).build());

        when(exerciseRepositoryMock.findById(any())).thenReturn(Optional.of(Exercise.builder().build()));

        exerciseService.save(exerciseSpy, "asdf");

        verify(exerciseSpy, times(0)).setCreated(any());
        verify(exerciseSpy, times(1)).setUpdated(any());
    }

    @Test
    public void saveNewSet() {
        Exercise exercise = Exercise.builder().name("Bench Press").sets(new ArrayList<>()).build();
        exercise.addSet(Set.builder().reps(5).lbs(135).build());
        exercise.addSet(Set.builder().reps(5).lbs(135).build());

        exerciseService.save(exercise, "asdf");

        exercise.getSets().forEach(set -> {
            assertNotNull(set.getExercise());
            assertNotNull(set.getCreated());
            assertNotNull(set.getUpdated());
        });
    }

    @Test
    public void saveExistingSet() {
        Exercise exercise = Exercise.builder().name("Bench Press").sets(new ArrayList<>()).build();

        Set set1 = spy(Set.builder().build());
        Set set2 = spy(Set.builder().build());

        exercise.addSet(set1);
        exercise.addSet(set2);

        when(setRepositoryMock.findById(any())).thenReturn(Optional.of(Set.builder().build()));

        exerciseService.save(exercise, "asdf");

        verify(set1, times(0)).setCreated(any());
        verify(set2, times(0)).setCreated(any());
    }
}