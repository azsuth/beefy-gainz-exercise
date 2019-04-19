package com.azs.beefygainz.exercise.repository;

import com.azs.beefygainz.exercise.model.Exercise;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ExerciseRepositoryIntegrationTest {

    @Autowired
    ExerciseRepository exerciseRepository;

    @Before
    public void setUp() {
        exerciseRepository.save(Exercise.builder().name("Bench Press").notes("Notes 1").userId("asdf").build());
        exerciseRepository.save(Exercise.builder().name("Lat Pulldowns").notes("Notes 2").userId("asdf").build());
        exerciseRepository.save(Exercise.builder().name("Squats").notes("Notes 3").userId("fdsa").build());
    }

    @Test
    public void findAllByUserId() {
        Iterable<Exercise> exercises = exerciseRepository.findAllByUserId("asdf");

        assertEquals(2, StreamSupport.stream(exercises.spliterator(), false).count());
        exercises.forEach(exercise -> {
            assertEquals("asdf", exercise.getUserId());
        });
    }

    @Test
    public void findByIdAndUserId() {
        Optional<Exercise> exercise = exerciseRepository.findByIdAndUserId(3L, "fdsa");

        assertTrue(exercise.isPresent());
        assertEquals("fdsa", exercise.get().getUserId());
        assertFalse(exerciseRepository.findByIdAndUserId(3L, "asdf").isPresent());
        assertFalse(exerciseRepository.findByIdAndUserId(4L, "fdsa").isPresent());
    }
}