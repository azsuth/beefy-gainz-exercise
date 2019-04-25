package com.azs.beefygainz.exercise.service;

import com.azs.beefygainz.exercise.exception.NoSuchSetException;
import com.azs.beefygainz.exercise.model.Exercise;
import com.azs.beefygainz.exercise.model.Set;
import com.azs.beefygainz.exercise.repository.SetRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class SetServiceImplTest {

    static final Long EXERCISE_ID = 1L;
    static final Long SET_ID = 1L;
    static final int SET_LBS = 125;
    static final int SET_REPS = 12;
    static final String SET_NOTES = "Notes";
    static final String USER_ID = "asdf";

    @Mock
    SetRepository setRepositoryMock;
    @Mock
    ExerciseService exerciseService;

    @InjectMocks
    SetServiceImpl setService;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void create() {
        Set setSpy = spy(Set.builder().build());

        when(exerciseService.getSavedExercise(any(), any())).thenReturn(Exercise.builder().id(EXERCISE_ID).build());
        when(setRepositoryMock.save(any())).thenReturn(setSpy);

        Set set = setService.create(setSpy, EXERCISE_ID, USER_ID);

        assertEquals(EXERCISE_ID, set.getExercise().getId());
        verify(setSpy).setCreated(any());
        verify(setSpy).setUpdated(any());
        verify(setRepositoryMock).save(any());
    }

    @Test
    public void create_existing() {
        Set set = Set.builder().id(SET_ID).build();
        Exercise exercise = Exercise.builder().build();
        SetServiceImpl setServiceSpy = spy(setService);

        when(exerciseService.getSavedExercise(any(), any())).thenReturn(exercise);
        when(setRepositoryMock.findByIdAndExercise(any(), any())).thenReturn(Optional.of(Set.builder().build()));

        setServiceSpy.create(set, EXERCISE_ID, USER_ID);

        verify(setServiceSpy).update(eq(SET_ID), eq(set), eq(exercise));
    }

    @Test
    public void create_defaultsLastRepsAndLbs() {
        List<Set> sets = new ArrayList<>();
        sets.add(Set.builder().lbs(135).reps(12).build());
        sets.add(Set.builder().lbs(205).reps(4).build());
        Exercise exercise = Exercise.builder().sets(sets).build();

        Set setSpy = spy(Set.builder().build());

        when(exerciseService.getSavedExercise(any(), any())).thenReturn(exercise);
        when(setRepositoryMock.save(any())).thenReturn(setSpy);

        Set set = setService.create(setSpy, EXERCISE_ID, USER_ID);

        assertEquals(4, set.getReps());
        assertEquals(205, set.getLbs());
        verify(setSpy).setReps(eq(4));
        verify(setSpy).setLbs(eq(205));
    }

    @Test
    public void update_exerciseId() {
        Set set = Set.builder().build();
        Exercise exercise = Exercise.builder().build();
        SetServiceImpl setServiceSpy = spy(setService);

        when(exerciseService.getSavedExercise(any(), any())).thenReturn(exercise);
        when(setRepositoryMock.findByIdAndExercise(any(), any())).thenReturn(Optional.of(Set.builder().build()));

        setServiceSpy.update(SET_ID, set, EXERCISE_ID, USER_ID);

        verify(setServiceSpy).update(eq(SET_ID), eq(set), eq(exercise));
        verify(exerciseService).getSavedExercise(eq(SET_ID), eq(USER_ID));
    }

    @Test
    public void update_exercise() {
        Set set = Set.builder().id(SET_ID).reps(SET_REPS).lbs(SET_LBS).notes(SET_NOTES).build();
        Set setSpy = spy(Set.builder().build());
        Exercise exerciseSpy = spy(Exercise.builder().build());

        when(setRepositoryMock.findByIdAndExercise(any(), any())).thenReturn(Optional.of(setSpy));
        when(setRepositoryMock.save(any())).thenReturn(setSpy);

        Set updatedSet = setService.update(SET_ID, set, exerciseSpy);

        assertEquals(SET_REPS, updatedSet.getReps());
        assertEquals(SET_LBS, updatedSet.getLbs());
        assertEquals(SET_NOTES, updatedSet.getNotes());

        verify(setSpy).setReps(eq(SET_REPS));
        verify(setSpy).setLbs(eq(SET_LBS));
        verify(setSpy).setNotes(eq(SET_NOTES));
        verify(setRepositoryMock).save(any());
    }

    @Test(expected = NoSuchSetException.class)
    public void getSavedSet_doesntExist() {
        when(setRepositoryMock.findByIdAndExercise(any(), any())).thenReturn(Optional.empty());

        setService.getSavedSet(SET_ID, Exercise.builder().build());
    }

    @Test(expected = NoSuchSetException.class)
    public void delete_setDoesntExist() {
        when(setRepositoryMock.findByIdAndExercise(any(), any())).thenReturn(Optional.empty());

        setService.delete(SET_ID, EXERCISE_ID, USER_ID);
    }

    @Test
    public void delete() {
        when(setRepositoryMock.findByIdAndExercise(any(), any())).thenReturn(Optional.of(Set.builder().build()));

        setService.delete(SET_ID, EXERCISE_ID, USER_ID);

        verify(setRepositoryMock).delete(any());
    }

    @Test
    public void getSavedSet() {
        Exercise exercise = Exercise.builder().build();

        when(setRepositoryMock.findByIdAndExercise(any(), any())).thenReturn(Optional.of(Set.builder().build()));

        setService.getSavedSet(SET_ID, exercise);

        verify(setRepositoryMock).findByIdAndExercise(eq(SET_ID), eq(exercise));
    }
}