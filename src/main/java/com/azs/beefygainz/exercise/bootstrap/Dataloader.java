package com.azs.beefygainz.exercise.bootstrap;

import com.azs.beefygainz.exercise.model.Exercise;
import com.azs.beefygainz.exercise.model.Set;
import com.azs.beefygainz.exercise.service.ExerciseService;
import com.azs.beefygainz.exercise.service.SetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({ "default", "docker" })
public class Dataloader implements CommandLineRunner {

    private final ExerciseService exerciseService;
    private final SetService setService;

    @Autowired
    public Dataloader(ExerciseService exerciseService, SetService setService) {
        this.exerciseService = exerciseService;
        this.setService = setService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (exerciseService.getAll().size() == 0) {
            loadData();
        }
    }

    private void loadData() {
        Exercise exercise = exerciseService.create(Exercise.builder().name("Bench Press").build(), "12345");

        Set set = exercise.getSets().get(0);
        set.setLbs(150);
        set.setReps(5);

        setService.update(set.getId(), set, exercise.getId(), "12345");
        setService.create(Set.builder().reps(5).lbs(150).build(), exercise.getId(), "12345");
        setService.create(Set.builder().reps(6).lbs(150).build(), exercise.getId(), "12345");

        exercise = exerciseService.create(Exercise.builder().name("Squats").build(), "12345");

        set = exercise.getSets().get(0);
        set.setLbs(165);
        set.setReps(5);

        setService.update(set.getId(), set, exercise.getId(), "12345");
        setService.create(Set.builder().reps(5).lbs(165).build(), exercise.getId(), "12345");
        setService.create(Set.builder().reps(5).lbs(165).build(), exercise.getId(), "12345");
        setService.create(Set.builder().reps(5).lbs(165).build(), exercise.getId(), "12345");
    }
}
