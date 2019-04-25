package com.azs.beefygainz.exercise.model;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class BaseEntityTest {

    @Test
    public void getExistingId() {
        assertEquals(Long.valueOf(1L), new BaseEntity(1L, LocalDateTime.now()).getId());
    }

    @Test
    public void getNullId() {
        assertEquals(Long.valueOf(-1L), new BaseEntity(null, LocalDateTime.now()).getId());
    }

    @Test
    public void isNew() {
        assertTrue(new BaseEntity(null, LocalDateTime.now()).isNew());
    }

    @Test
    public void isExisting() {
        assertFalse(new BaseEntity(1L, LocalDateTime.now()).isNew());
    }
}