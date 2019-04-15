package com.azs.beefygainz.exercise.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
@Data
@NoArgsConstructor
@Builder
public class Exercise extends BaseEntity {

    private String name;
    private String userId;

    @Lob
    private String notes;
}
