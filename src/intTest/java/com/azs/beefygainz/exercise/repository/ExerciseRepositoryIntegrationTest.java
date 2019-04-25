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

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(properties = {"spring.cloud.config.fail-fast=false"})
public class ExerciseRepositoryIntegrationTest {

    @Autowired
    ExerciseRepository exerciseRepository;

    @Before
    public void setUp() {
        Exercise benchPress = Exercise.builder().name("Bench Press").notes("Notes 1").userId("asdf").build();

        benchPress.addSet(Set.builder().lbs(125).reps(8).created(LocalDateTime.now().minusDays(4)).build());
        benchPress.addSet(Set.builder().lbs(125).reps(7).created(LocalDateTime.now().minusDays(4).plusMinutes(1)).build());
        benchPress.addSet(Set.builder().lbs(125).reps(6).created(LocalDateTime.now().minusDays(4).plusMinutes(2)).build());
        benchPress.addSet(Set.builder().lbs(135).reps(6).created(LocalDateTime.now().minusHours(1)).build());
        benchPress.addSet(Set.builder().lbs(135).reps(5).created(LocalDateTime.now().minusHours(1).plusMinutes(2)).build());

        Exercise latPulldown = Exercise.builder().name("Lat Pulldowns").notes("Notes 2").userId("asdf").build();

        latPulldown.addSet(Set.builder().lbs(105).reps(12).created(LocalDateTime.now().minusDays(1)).build());
        latPulldown.addSet(Set.builder().lbs(105).reps(12).created(LocalDateTime.now().minusHours(1).plusMinutes(15)).build());

        Exercise squat = Exercise.builder().name("Squats").notes("Notes 3").userId("fdsa").build();

        squat.addSet(Set.builder().lbs(155).reps(6).created(LocalDateTime.now().minusMinutes(30)).build());

        exerciseRepository.save(benchPress);
        exerciseRepository.save(latPulldown);
        exerciseRepository.save(squat);
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

    @Test
    public void findAllCurrentByUserId() {
        Iterable<Exercise> exercises = exerciseRepository.findAllCurrentByUserId("asdf", LocalDateTime.now().minusDays(1));

        assertEquals(2, StreamSupport.stream(exercises.spliterator(), false).count());
    }
}