package com.azs.beefygainz.exercise.controller;

import com.azs.beefygainz.exercise.model.Exercise;
import com.azs.beefygainz.exercise.service.ExerciseService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.azs.beefygainz.exercise.ExerciseApplicationTests.asJsonString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ExerciseControllerTest {

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
        exercises.add(Exercise.builder().name("Bench Press").build());
        exercises.add(Exercise.builder().name("Squats").build());

        when(exerciseServiceMock.findAllByUserId(anyString())).thenReturn(exercises);

        mockMvc.perform(get("/exercises").header("Auth-Token", "asdf"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Bench Press")))
                .andExpect(jsonPath("$[1].name", is("Squats")));

        verify(exerciseServiceMock).findAllByUserId("asdf");
    }

    @Test
    public void saveExercise() throws Exception {
        Exercise mockExercise = Exercise.builder().name("Bench Press").build();

        when(exerciseServiceMock.save(any(), anyString())).thenReturn(mockExercise);

        mockMvc.perform(post("/exercises")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Auth-Token", "asdf")
                .content(asJsonString(mockExercise)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Bench Press")));

        verify(exerciseServiceMock).save(any(), eq("asdf"));
    }
}