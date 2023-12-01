package com.example.artmind.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UserModelTest {
    UserModel userModel = new UserModel();

    @Test
    public void testVerifyUsername_emptyUsername_returnsTrue() {
        assertTrue(userModel.verifyUsername(""));
    }

    @Test
    public void testVerifyUsername_shortUsername_returnsTrue() {
        assertTrue(userModel.verifyUsername("ab"));
    }

    @Test
    public void testVerifyUsername_longUsername_returnsTrue() {
        assertTrue(userModel.verifyUsername("abcdefghijk"));
    }

    @Test
    public void testVerifyUsername_validUsername_returnsFalse() {
        assertFalse(userModel.verifyUsername("john_doe"));
    }
}
