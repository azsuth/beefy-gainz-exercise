package com.azs.beefygainz.exercise.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Lob;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Exercise extends BaseEntity {

    private String name;
    private String userId;

    @Lob
    private String notes;
}
