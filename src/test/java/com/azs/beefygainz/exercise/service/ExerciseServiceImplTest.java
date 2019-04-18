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

    static final String USER_ID = "asdf";
    static final Long EXERCISE_ID = 1L;
    static final String EXERCISE_NAME = "Bench Press";
    static final String EXERCISE_NOTES = "Notes";

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

        List<Exercise> exercises = exerciseService.findAllByUserId(USER_ID);

        assertEquals(2, exercises.size());
        verify(exerciseRepositoryMock).findAllByUserId(USER_ID);
    }

    @Test
    public void create() {
        Exercise exerciseSpy = spy(Exercise.builder().build());

        when(exerciseRepositoryMock.save(exerciseSpy)).thenReturn(exerciseSpy);

        Exercise exercise = exerciseService.create(exerciseSpy, USER_ID);

        assertNotNull(exercise);
        verify(exerciseSpy).setCreated(any());
        verify(exerciseSpy).setUpdated(any());
        verify(exerciseSpy).setUserId(eq(USER_ID));
    }

    @Test
    public void create_existing() {
        Exercise exercise = Exercise.builder().id(EXERCISE_ID).build();
        ExerciseServiceImpl exerciseServiceSpy = spy(exerciseService);

        when(exerciseRepositoryMock.findByIdAndUserId(EXERCISE_ID, USER_ID))
                .thenReturn(Optional.of(Exercise.builder().build()));

        exerciseServiceSpy.create(exercise, USER_ID);

        verify(exerciseServiceSpy).update(eq(EXERCISE_ID), eq(exercise), eq(USER_ID));
    }

    @Test(expected = NoSuchExerciseException.class)
    public void update_exerciseDoesntExist() {
        Exercise exercise = Exercise.builder().userId(USER_ID).build();

        when(exerciseRepositoryMock.findByIdAndUserId(EXERCISE_ID, USER_ID)).thenReturn(Optional.empty());

        exerciseService.update(EXERCISE_ID, exercise, USER_ID);
    }

    @Test
    public void update() {
        Exercise exercise = Exercise.builder()
                .id(EXERCISE_ID)
                .name(EXERCISE_NAME)
                .notes(EXERCISE_NOTES)
                .build();

        Exercise exerciseSpy = spy(Exercise.builder().build());

        when(exerciseRepositoryMock.findByIdAndUserId(EXERCISE_ID, USER_ID))
                .thenReturn(Optional.of(exerciseSpy));
        when(exerciseRepositoryMock.save(exerciseSpy)).thenReturn(exerciseSpy);

        Exercise updatedExercise = exerciseService.update(EXERCISE_ID, exercise, USER_ID);

        assertEquals(EXERCISE_NAME, updatedExercise.getName());
        assertEquals(EXERCISE_NOTES, updatedExercise.getNotes());

        verify(exerciseSpy).setName(eq(EXERCISE_NAME));
        verify(exerciseSpy).setNotes(eq(EXERCISE_NOTES));
        verify(exerciseSpy).setUpdated(any());
        verify(exerciseSpy, times(0)).setCreated(any());
        verify(exerciseRepositoryMock).save(any());
    }

    @Test(expected = NoSuchExerciseException.class)
    public void delete_exerciseDoesntExist() {
        when(exerciseRepositoryMock.findByIdAndUserId(any(), any())).thenReturn(Optional.empty());

        exerciseService.delete(EXERCISE_ID, USER_ID);
    }

    @Test
    public void delete() {
        when(exerciseRepositoryMock.findByIdAndUserId(any(), any()))
                .thenReturn(Optional.of(Exercise.builder().build()));

        exerciseService.delete(EXERCISE_ID, USER_ID);

        verify(exerciseRepositoryMock).delete(any());
    }

    @Test(expected = NoSuchExerciseException.class)
    public void getSavedExercise_doesntExist() {
        when(exerciseRepositoryMock.findByIdAndUserId(any(), any())).thenReturn(Optional.empty());

        exerciseService.getSavedExercise(EXERCISE_ID, USER_ID);
    }

    @Test
    public void getSavedExercise() {
        when(exerciseRepositoryMock.findByIdAndUserId(any(), any()))
                .thenReturn(Optional.of(Exercise.builder().build()));

        exerciseService.getSavedExercise(EXERCISE_ID, USER_ID);

        verify(exerciseRepositoryMock).findByIdAndUserId(eq(EXERCISE_ID), eq(USER_ID));
    }
}