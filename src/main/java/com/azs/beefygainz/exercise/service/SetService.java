package com.azs.beefygainz.exercise.service;

import com.azs.beefygainz.exercise.model.Set;

public interface SetService {
    Set save(Set set, Long exerciseId);
}
