package com.azs.beefygainz.exercise.controller;

import com.azs.beefygainz.exercise.model.Exercise;
import com.azs.beefygainz.exercise.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping("")
    public List<Exercise> get(@RequestHeader("userId") String userId, @RequestParam(required = false) boolean current, @RequestParam(required=false) String search) {
        return exerciseService.getAll(userId, current, search);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Exercise create(@RequestHeader("userId") String userId, @RequestBody Exercise exercise) {
        return exerciseService.create(exercise, userId);
    }

    @PutMapping("/{exerciseId}")
    public Exercise update(@RequestHeader("userId") String userId,
                           @PathVariable Long exerciseId,
                           @RequestBody Exercise exercise) {
        return exerciseService.update(exerciseId, exercise, userId);
    }

    @DeleteMapping("/{exerciseId}")
    public void delete(@RequestHeader("userId") String userId, @PathVariable Long exerciseId) {
        exerciseService.delete(exerciseId, userId);
    }
}
