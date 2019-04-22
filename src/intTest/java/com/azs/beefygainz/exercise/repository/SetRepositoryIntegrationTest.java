package com.azs.beefygainz.exercise.repository;

import com.azs.beefygainz.exercise.model.Exercise;
import com.azs.beefygainz.exercise.model.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(properties = { "spring.cloud.config.fail-fast=false" })
public class SetRepositoryIntegrationTest {

    @Autowired
    ExerciseRepository exerciseRepository;
    @Autowired
    SetRepository setRepository;

    @Before
    public void setUp() {
        Exercise exercise1 = Exercise.builder().build();
        exercise1.addSet(Set.builder().reps(12).lbs(125).exercise(exercise1).notes("Notes 1").build());
        exercise1.addSet(Set.builder().reps(13).lbs(125).exercise(exercise1).notes("Notes 2").build());

        Exercise exercise2 = Exercise.builder().build();
        exercise2.addSet(Set.builder().reps(6).lbs(175).exercise(exercise2).notes("Notes2").build());

        exerciseRepository.save(exercise1);
        exerciseRepository.save(exercise2);
    }

    @Test
    public void findByIdAndExercise() {
        Optional<Set> set = setRepository.findByIdAndExercise(1L, Exercise.builder().id(1L).build());

        assertTrue(set.isPresent());
        assertEquals(Long.valueOf(1L), set.get().getExercise().getId());
        assertFalse(setRepository.findByIdAndExercise(3L, Exercise.builder().id(1L).build()).isPresent());
    }
}