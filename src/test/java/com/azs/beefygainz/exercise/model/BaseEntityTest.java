package com.azs.beefygainz.exercise.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class BaseEntityTest {

    @Test
    public void getExistingId() {
        assertEquals(Long.valueOf(1L), new BaseEntity(1L).getId());
    }

    @Test
    public void getNullId() {
        assertEquals(Long.valueOf(-1L), new BaseEntity(null).getId());
    }

    @Test
    public void isNew() {
        assertTrue(new BaseEntity(null).isNew());
    }

    @Test
    public void isExisting() {
        assertFalse(new BaseEntity(1L).isNew());
    }
}