package com.azs.beefygainz.exercise.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Exercise extends BaseEntity {

    private String name;
    private String userId;

    @Lob
    private String notes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exercise")
    private List<Set> sets = new ArrayList<>();

    @Builder
    public Exercise(Long id, String name, String userId, String notes, List<Set> sets) {
        super(id);
        this.name = name;
        this.userId = userId;
        this.notes = notes;
        this.sets = sets;

        if (sets == null) {
            this.sets = new ArrayList<>();
        }
    }

    public void addSet(Set set) {
        set.setExercise(this);
        sets.add(set);
    }
}
