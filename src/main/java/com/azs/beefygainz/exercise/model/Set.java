package com.azs.beefygainz.exercise.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Set extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;
    private int reps;
    private int lbs;
    private String notes;
}
