package com.azs.beefygainz.exercise.controller;

import com.azs.beefygainz.exercise.model.Exercise;
import com.azs.beefygainz.exercise.service.ExerciseService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.azs.beefygainz.exercise.ExerciseApplicationTests.asJsonString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ExerciseControllerTest {

    static final String USER_ID = "asdf";
    static final Long EXERCISE_ID = 1L;
    static final String EXERCISE_NAME = "Bench Press";
    static final String EXERCISE_NOTES = "Notes";

    @Mock
    ExerciseService exerciseServiceMock;

    @InjectMocks
    ExerciseController exerciseController;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(exerciseController).build();
    }

    @Test
    public void getExercises() throws Exception {
        List<Exercise> exercises = new ArrayList<>();
        exercises.add(Exercise.builder().name(EXERCISE_NAME).build());
        exercises.add(Exercise.builder().name("Squats").build());

        when(exerciseServiceMock.findAllByUserId(anyString())).thenReturn(exercises);

        mockMvc.perform(get("/exercises").header("userId", USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(EXERCISE_NAME)))
                .andExpect(jsonPath("$[1].name", is("Squats")));

        verify(exerciseServiceMock).findAllByUserId(USER_ID);
    }

    @Test
    public void create() throws Exception {
        Exercise mockExercise = Exercise.builder().name(EXERCISE_NAME).build();

        when(exerciseServiceMock.create(any(), any())).thenReturn(mockExercise);

        mockMvc.perform(post("/exercises")
                .contentType(MediaType.APPLICATION_JSON)
                .header("userId", USER_ID)
                .content(asJsonString(mockExercise)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(EXERCISE_NAME)));

        verify(exerciseServiceMock).create(any(), eq(USER_ID));
    }

    @Test
    public void update() throws Exception {
        Exercise exercise = Exercise.builder().id(EXERCISE_ID).name(EXERCISE_NAME).notes(EXERCISE_NOTES).build();

        when(exerciseServiceMock.update(any(), any(), any())).thenReturn(exercise);

        mockMvc.perform(put("/exercises/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("userId", USER_ID)
                .content(asJsonString(exercise)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));

        verify(exerciseServiceMock).update(eq(EXERCISE_ID), any(), eq(USER_ID));
    }

    @Test
    public void deleteExercise() throws Exception {
        mockMvc.perform(delete("/exercises/1")
                .header("userId", USER_ID))
                .andExpect(status().isOk());

        verify(exerciseServiceMock).delete(eq(EXERCISE_ID), eq(USER_ID));
    }
}