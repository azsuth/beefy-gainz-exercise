package com.azs.beefygainz.exercise.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Set extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    @JsonIgnore
    private Exercise exercise;
    private int reps;
    private int lbs;
    private String notes;

    @Builder
    public Set(Long id, Exercise exercise, int reps, int lbs, String notes) {
        super(id);
        this.exercise = exercise;
        this.reps = reps;
        this.lbs = lbs;
        this.notes = notes;
    }
}
