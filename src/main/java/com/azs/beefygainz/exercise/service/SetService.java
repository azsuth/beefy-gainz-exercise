package com.azs.beefygainz.exercise.service;

import com.azs.beefygainz.exercise.model.Set;

public interface SetService {
    Set create(Set set, Long exerciseId, String userId);

    Set update(Long setId, Set set, Long exerciseId, String userId);

    void delete(Long setId, Long exerciseId, String userId);
}
