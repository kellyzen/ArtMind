package com.example.artmind.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UserModelTest {
    @Test
    public void testVerifyUsername_emptyUsername_returnsTrue() {
        assertTrue(UserModel.verifyUsername(""));
    }

    @Test
    public void testVerifyUsername_shortUsername_returnsTrue() {
        assertTrue(UserModel.verifyUsername("ab"));
    }

    @Test
    public void testVerifyUsername_longUsername_returnsTrue() {
        assertTrue(UserModel.verifyUsername("abcdefghijk"));
    }

    @Test
    public void testVerifyUsername_validUsername_returnsFalse() {
        assertFalse(UserModel.verifyUsername("john_doe"));
    }
}
