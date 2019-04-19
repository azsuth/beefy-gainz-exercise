package com.azs.beefygainz.exercise.controller;

import com.azs.beefygainz.exercise.model.Set;
import com.azs.beefygainz.exercise.service.SetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exercises/{exerciseId}/sets")
public class SetController {

    private final SetService setService;

    @Autowired
    public SetController(SetService setService) {
        this.setService = setService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Set create(@RequestHeader("userId") String userId, @PathVariable Long exerciseId, @RequestBody Set set) {
        return setService.create(set, exerciseId, userId);
    }

    @PutMapping("/{setId}")
    public Set update(@RequestHeader("userId") String userId,
                      @PathVariable Long exerciseId,
                      @PathVariable Long setId,
                      @RequestBody Set set) {
        return setService.update(setId, set, exerciseId, userId);
    }

    @DeleteMapping("/{setId}")
    public void delete(@RequestHeader("userId") String userId, @PathVariable Long exerciseId, @PathVariable Long setId) {
        setService.delete(setId, exerciseId, userId);
    }
}
