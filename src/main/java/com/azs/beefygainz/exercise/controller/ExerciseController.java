package com.azs.beefygainz.exercise.controller;

import com.azs.beefygainz.exercise.model.Exercise;
import com.azs.beefygainz.exercise.model.Set;
import com.azs.beefygainz.exercise.service.ExerciseService;
import com.azs.beefygainz.exercise.service.SetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;
    private final SetService setService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService, SetService setService) {
        this.exerciseService = exerciseService;
        this.setService = setService;
    }

    @GetMapping("")
    public List<Exercise> getExercises(@RequestHeader("userId") String userId) {
        return exerciseService.findAllByUserId(userId);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Exercise saveExercise(@RequestHeader("userId") String userId, @RequestBody Exercise exercise) {
        return exerciseService.save(exercise, userId);
    }

    @PostMapping("/{exerciseId}/sets")
    @ResponseStatus(HttpStatus.CREATED)
    public Set saveSet(@RequestHeader("userId") String userId, @RequestBody Set set, @PathVariable Long exerciseId) {
        return setService.save(set, exerciseId);
    }
}
