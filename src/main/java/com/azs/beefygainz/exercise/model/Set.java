package com.azs.beefygainz.exercise.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, exclude = { "exercise" })
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
    public Set(Long id, LocalDateTime created, Exercise exercise, int reps, int lbs, String notes) {
        super(id, created);
        this.exercise = exercise;
        this.reps = reps;
        this.lbs = lbs;
        this.notes = notes;
    }
}
