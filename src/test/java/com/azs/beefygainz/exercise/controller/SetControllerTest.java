package com.azs.beefygainz.exercise.controller;

import com.azs.beefygainz.exercise.model.Set;
import com.azs.beefygainz.exercise.service.SetService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.azs.beefygainz.exercise.helper.TestHelper.asJsonString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SetControllerTest {

    static final String USER_ID = "asdf";

    @Mock
    SetService setServiceMock;

    @InjectMocks
    SetController setController;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(setController).build();
    }

    @Test
    public void create() throws Exception {
        Set set = Set.builder().reps(12).lbs(125).build();

        when(setServiceMock.create(any(), any(), any())).thenReturn(set);

        mockMvc.perform(post("/exercises/14/sets")
                .contentType(MediaType.APPLICATION_JSON)
                .header("userId", USER_ID)
                .content(asJsonString(set)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.reps", is(12)));

        verify(setServiceMock).create(any(), eq(14L), eq(USER_ID));
    }

    @Test
    public void update() throws Exception {
        Set set = Set.builder().id(11L).reps(12).lbs(125).build();

        when(setServiceMock.update(any(), any(), any(), any())).thenReturn(set);

        mockMvc.perform(put("/exercises/14/sets/11")
                .contentType(MediaType.APPLICATION_JSON)
                .header("userId", USER_ID)
                .content(asJsonString(set)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(11)));

        verify(setServiceMock).update(eq(11L), any(), eq(14L), eq(USER_ID));
    }

    @Test
    public void deleteSet() throws Exception {
        mockMvc.perform(delete("/exercises/14/sets/11")
                .header("userId", USER_ID))
                .andExpect(status().isOk());

        verify(setServiceMock).delete(eq(11L), eq(14L), eq(USER_ID));
    }
}