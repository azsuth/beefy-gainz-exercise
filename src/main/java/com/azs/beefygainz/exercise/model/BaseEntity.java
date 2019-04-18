package com.azs.beefygainz.exercise.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime created;
    private LocalDateTime updated;

    public BaseEntity(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public boolean isNew() {
        return getId() < 0;
    }

    public Long getId() {
        return Optional.ofNullable(id).orElse(-1L);
    }
}
